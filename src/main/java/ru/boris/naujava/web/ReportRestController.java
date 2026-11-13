package ru.boris.naujava.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.boris.naujava.entity.Report;
import ru.boris.naujava.entity.ReportStatus;
import ru.boris.naujava.service.ReportService;

import java.util.Map;

@RestController
@RequestMapping("/custom/reports")
public class ReportRestController {
    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);
        return ResponseEntity.accepted().body(Map.of(
                "id", reportId,
                "status", ReportStatus.CREATED.name(),
                "message", "Отчёт создан. Для получения результата запросите GET /custom/reports/" + reportId
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);
        if (report.getStatus() == ReportStatus.CREATED) {
            return ResponseEntity.accepted()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("id", id, "status", "CREATED",
                            "message", "Отчёт ещё формируется"));
        }
        if (report.getStatus() == ReportStatus.ERROR) {
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("id", id, "status", "ERROR",
                            "message", "При формировании отчёта произошла ошибка",
                            "details", report.getContent() == null ? "" : report.getContent()));
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML)
                .body(report.getContent());
    }
}
