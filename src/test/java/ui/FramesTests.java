package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static steps.WebFormSteps.openFramesPage;

public class FramesTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openFramesPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void FramesTest() {
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("lead")));

        WebElement frameBodyElement = driver.findElement(By.name("frame-body"));
        driver.switchTo().frame(frameBodyElement);

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("display-6")));
        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum dolor sit amet consectetur adipiscing");

        driver.switchTo().defaultContent();

        WebElement frameHeaderElement = driver.findElement(By.name("frame-header"));
        driver.switchTo().frame(frameHeaderElement);

        assertThat(driver.findElement(By.className("display-6")).getText()).contains("Frames");
    }
}