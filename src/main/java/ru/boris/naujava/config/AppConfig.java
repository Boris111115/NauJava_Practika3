package ru.boris.naujava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.boris.naujava.console.CommandProcessor;
import ru.boris.naujava.entity.PatientTransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Configuration
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<PatientTransfer> patientTransferContainer() {
        return new ArrayList<>();
    }

    @Bean
    public String applicationInfo() {

        String info =
                "Название приложения: " + appName +
                        ", версия: " + appVersion;

        System.out.println(info);

        return info;
    }

    @Bean
    public CommandLineRunner commandScanner(
            CommandProcessor commandProcessor) {

        return args -> {

            try (Scanner scanner = new Scanner(System.in)) {

                System.out.println();
                System.out.println(
                        "Система контроля передачи пациентов СМП"
                );
                System.out.println(
                        "Введите 'help' для просмотра команд."
                );
                System.out.println(
                        "Введите 'exit' для завершения."
                );

                while (true) {

                    System.out.print("> ");

                    String input = scanner.nextLine();

                    if ("exit".equalsIgnoreCase(input.trim())) {

                        System.out.println(
                                "Выход из программы..."
                        );

                        break;
                    }

                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}