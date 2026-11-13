package ru.boris.naujava.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Запуск: RUN_SELENIUM=true, APP_BASE_URL=http://localhost:8080, TEST_USERNAME=admin,
 * TEST_PASSWORD=ChangeMe123! (или собственная тестовая учётная запись).
 */
@EnabledIfEnvironmentVariable(named = "RUN_SELENIUM", matches = "true")
class LoginLogoutSeleniumTest {

    private WebDriver driver;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = env("APP_BASE_URL", "http://localhost:8080");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new org.openqa.selenium.chrome.ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void userCanLoginAndLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
                .sendKeys(env("TEST_USERNAME", "admin"));
        driver.findElement(By.name("password")).sendKeys(env("TEST_PASSWORD", "ChangeMe123!"));
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/patients/view"));
        assertTrue(driver.getPageSource().contains("Список пациентов"));

        driver.findElement(By.id("logout-button")).click();
        wait.until(ExpectedConditions.urlContains("/login"));
        assertTrue(driver.getPageSource().contains("Вход в систему"));
    }

    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
