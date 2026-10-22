package ru.boris.naujava.console;

import org.springframework.stereotype.Component;
import ru.boris.naujava.entity.PatientTransfer;
import ru.boris.naujava.service.PatientTransferService;

import java.util.List;

@Component
public class CommandProcessor {

    private final PatientTransferService service;

    public CommandProcessor(PatientTransferService service) {
        this.service = service;
    }

    public void processCommand(String input) {

        if (input == null || input.isBlank()) {
            System.out.println("Команда не введена.");
            return;
        }

        String[] cmd = input.trim().split("\\s+");

        try {

            switch (cmd[0].toLowerCase()) {

                case "create" -> {

                    if (cmd.length != 7) {
                        System.out.println(
                                "Использование: create <id> <patientId> " +
                                        "<ambulance> <hospital> <status> <minutes>"
                        );
                        return;
                    }

                    service.createTransfer(
                            Long.parseLong(cmd[1]),
                            cmd[2],
                            cmd[3],
                            cmd[4],
                            cmd[5],
                            Integer.parseInt(cmd[6])
                    );

                    System.out.println(
                            "Передача пациента успешно создана."
                    );
                }

                case "read" -> {

                    if (cmd.length != 2) {
                        System.out.println(
                                "Использование: read <id>"
                        );
                        return;
                    }

                    Long id = Long.parseLong(cmd[1]);

                    PatientTransfer transfer =
                            service.findById(id);

                    if (transfer == null) {
                        System.out.println(
                                "Передача не найдена."
                        );
                    } else {
                        System.out.println(transfer);
                    }
                }

                case "update" -> {

                    if (cmd.length != 7) {
                        System.out.println(
                                "Использование: update <id> <patientId> " +
                                        "<ambulance> <hospital> <status> <minutes>"
                        );
                        return;
                    }

                    service.updateTransfer(
                            Long.parseLong(cmd[1]),
                            cmd[2],
                            cmd[3],
                            cmd[4],
                            cmd[5],
                            Integer.parseInt(cmd[6])
                    );

                    System.out.println(
                            "Передача успешно обновлена."
                    );
                }

                case "delete" -> {

                    if (cmd.length != 2) {
                        System.out.println(
                                "Использование: delete <id>"
                        );
                        return;
                    }

                    service.deleteById(
                            Long.parseLong(cmd[1])
                    );

                    System.out.println(
                            "Передача успешно удалена."
                    );
                }

                case "list" -> {

                    List<PatientTransfer> transfers =
                            service.findAll();

                    if (transfers.isEmpty()) {
                        System.out.println(
                                "Список передач пуст."
                        );
                    } else {
                        transfers.forEach(System.out::println);
                    }
                }

                case "check" -> {

                    if (cmd.length != 2) {
                        System.out.println(
                                "Использование: check <id>"
                        );
                        return;
                    }

                    Long id = Long.parseLong(cmd[1]);

                    boolean delayed =
                            service.isTransferDelayed(id);

                    if (delayed) {
                        System.out.println(
                                "ВНИМАНИЕ: норматив передачи превышен!"
                        );
                    } else {
                        System.out.println(
                                "Передача находится в пределах норматива."
                        );
                    }
                }

                case "help" -> printHelp();

                default ->
                        System.out.println(
                                "Неизвестная команда. Используйте help."
                        );
            }

        } catch (NumberFormatException e) {

            System.out.println(
                    "Ошибка: числовое значение введено неверно."
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "Ошибка: " + e.getMessage()
            );
        }
    }

    private void printHelp() {

        System.out.println();
        System.out.println("Доступные команды:");
        System.out.println(
                "create <id> <patientId> <ambulance> " +
                        "<hospital> <status> <minutes>"
        );
        System.out.println("read <id>");
        System.out.println(
                "update <id> <patientId> <ambulance> " +
                        "<hospital> <status> <minutes>"
        );
        System.out.println("delete <id>");
        System.out.println("list");
        System.out.println("check <id>");
        System.out.println("help");
        System.out.println("exit");
        System.out.println();
    }
}