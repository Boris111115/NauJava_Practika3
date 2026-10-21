package ru.boris.naujava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<PatientTransfer> patientTransferContainer() {
        return new ArrayList<>();
    }

    @Bean
    @ConditionalOnProperty(
            name = "app.console.enabled",
            havingValue = "true",
            matchIfMissing = true
    )
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
                        System.out.println("Выход из программы...");
                        break;
                    }

                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}
