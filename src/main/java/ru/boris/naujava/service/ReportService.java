package ru.boris.naujava.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.entity.Report;
import ru.boris.naujava.entity.ReportStatus;
import ru.boris.naujava.repository.PatientTransferRepository;
import ru.boris.naujava.repository.ReportRepository;
import ru.boris.naujava.repository.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PatientTransferRepository transferRepository;

    public ReportService(ReportRepository reportRepository,
                         UserRepository userRepository,
                         PatientTransferRepository transferRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public Long createReport() {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        return reportRepository.save(report).getId();
    }

    // CompletableFuture запускает формирование отчёта в фоновом потоке.
    public CompletableFuture<Void> generateReportAsync(Long reportId) {
        return CompletableFuture.runAsync(() -> generateReport(reportId));
    }

    @Transactional(readOnly = true)
    public Report getReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Отчёт с id " + reportId + " не найден"));
    }

    private void generateReport(Long reportId) {
        long totalStart = System.currentTimeMillis();
        try {
            AtomicLong usersCount = new AtomicLong();
            AtomicLong usersElapsed = new AtomicLong();
            AtomicReference<List<PatientTransfer>> transfers =
                    new AtomicReference<>(List.of());
            AtomicLong transfersElapsed = new AtomicLong();
            AtomicReference<RuntimeException> taskError = new AtomicReference<>();

            Thread usersThread = new Thread(() -> {
                long start = System.currentTimeMillis();
                try {
                    usersCount.set(userRepository.count());
                } catch (RuntimeException exception) {
                    taskError.compareAndSet(null, exception);
                } finally {
                    usersElapsed.set(System.currentTimeMillis() - start);
                }
            }, "report-users-counter");

            Thread transfersThread = new Thread(() -> {
                long start = System.currentTimeMillis();
                try {
                    transfers.set(transferRepository.findAll());
                } catch (RuntimeException exception) {
                    taskError.compareAndSet(null, exception);
                } finally {
                    transfersElapsed.set(System.currentTimeMillis() - start);
                }
            }, "report-transfers-loader");

            usersThread.start();
            transfersThread.start();
            usersThread.join();
            transfersThread.join();
            if (taskError.get() != null) {
                throw taskError.get();
            }

            long totalElapsed = System.currentTimeMillis() - totalStart;
            String html = buildHtml(usersCount.get(), transfers.get(),
                    usersElapsed.get(), transfersElapsed.get(), totalElapsed);

            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Отчёт с id " + reportId + " не найден"));
            report.setContent(html);
            report.setStatus(ReportStatus.COMPLETED);
            reportRepository.save(report);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            markAsError(reportId, "Формирование отчёта было прервано");
        } catch (Exception exception) {
            markAsError(reportId, exception.getMessage() == null
                    ? "Неизвестная ошибка" : exception.getMessage());
        }
    }

    private void markAsError(Long reportId, String message) {
        reportRepository.findById(reportId).ifPresent(report -> {
            report.setStatus(ReportStatus.ERROR);
            report.setContent("<html><body><h1>Ошибка формирования отчёта</h1><p>"
                    + escapeHtml(message) + "</p></body></html>");
            reportRepository.save(report);
        });
    }

    private String buildHtml(long usersCount, List<PatientTransfer> transfers,
                             long usersElapsed, long transfersElapsed,
                             long totalElapsed) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang='ru'><head><meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        html.append("<title>Статистический отчёт</title><style>");
        html.append("body{font-family:Arial,sans-serif;margin:32px;color:#242424;}");
        html.append("h1,h2{color:#56358c;}table{border-collapse:collapse;width:100%;margin:16px 0 28px;}");
        html.append("th,td{border:1px solid #d8d1e3;padding:10px;text-align:left;}");
        html.append("th{background:#eee8f7;}tr:nth-child(even){background:#faf8fc;}");
        html.append("</style></head><body><h1>Статистический отчёт приложения</h1>");
        html.append("<h2>Общая статистика</h2><table>");
        html.append("<tr><th>Показатель</th><th>Значение</th></tr>");
        html.append("<tr><td>Количество зарегистрированных пользователей</td><td>")
                .append(usersCount).append("</td></tr>");
        html.append("<tr><td>Время подсчёта пользователей</td><td>")
                .append(usersElapsed).append(" мс</td></tr>");
        html.append("<tr><td>Время получения списка передач пациентов</td><td>")
                .append(transfersElapsed).append(" мс</td></tr>");
        html.append("<tr><td>Общее время формирования отчёта</td><td>")
                .append(totalElapsed).append(" мс</td></tr></table>");
        html.append("<h2>Список передач пациентов</h2><table>");
        html.append("<tr><th>ID</th><th>ID пациента</th><th>Номер бригады</th>");
        html.append("<th>Больница</th><th>Статус</th><th>Время передачи, мин.</th></tr>");
        for (PatientTransfer transfer : transfers) {
            html.append("<tr><td>").append(transfer.getId())
                    .append("</td><td>").append(escapeHtml(transfer.getPatientId()))
                    .append("</td><td>").append(escapeHtml(transfer.getAmbulanceNumber()))
                    .append("</td><td>").append(escapeHtml(transfer.getHospitalName()))
                    .append("</td><td>").append(escapeHtml(transfer.getStatus()))
                    .append("</td><td>").append(transfer.getTransferTimeMinutes())
                    .append("</td></tr>");
        }
        html.append("</table></body></html>");
        return html.toString();
    }

    private String escapeHtml(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
