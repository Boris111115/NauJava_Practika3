
package ru.boris.naujava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "ru.boris.naujava.repository"
)
public class JpaRepositoryConfig {
}
