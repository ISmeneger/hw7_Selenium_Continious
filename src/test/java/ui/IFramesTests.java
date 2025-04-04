package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static steps.WebFormSteps.openIFramesPage;

public class IFramesTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openIFramesPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @Tag("positive")
    void IFramesTest() {
        WebElement iframeElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iframeElement);

        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum dolor sit amet ");

        driver.switchTo().defaultContent();
        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");
    }

    @Test
    @Tag("negative")
    void IFramesInvalidTest() {
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("lead")));

        WebElement iFrameElement = driver.findElement(By.id("my-iframe"));
        driver.switchTo().frame(iFrameElement);

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("display-6")));
    }
}
