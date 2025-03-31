package ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static steps.WebFormSteps.openDialogBoxesPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DialogBoxesTests {
    WebDriver driver;
    WebDriverWait wait;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openDialogBoxesPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void launchAlertTest() throws InterruptedException {
        driver.findElement(By.id("my-alert")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert launchAlert = driver.switchTo().alert();
        String launchAlertText = launchAlert.getText();
        assertThat(launchAlertText).isEqualTo("Hello world!");
        Thread.sleep(2000);
        launchAlert.accept();
    }

    @Test
    @Order(2)
    void launchConfirmTest() throws InterruptedException {
        WebElement launchConfirmButton = driver.findElement(By.id("my-confirm"));
        launchConfirmButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert launchConfirm = driver.switchTo().alert();
        String launchAlertText = launchConfirm.getText();
        assertThat(launchAlertText).isEqualTo("Is this correct?");
        launchConfirm.accept();
        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: true");
        Thread.sleep(2000);

        launchConfirmButton.click();
        launchConfirm.dismiss();
        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: false");
        Thread.sleep(2000);
    }

    @Test
    @Order(3)
    void launchPromptTest() throws InterruptedException {
        WebElement launchPromptButton = driver.findElement(By.id("my-prompt"));
        launchPromptButton.click();
        Alert launchPrompt = driver.switchTo().alert();
        String launchPromptText = launchPrompt.getText();
        assertThat(launchPromptText).isEqualTo("Please enter your name");
        launchPrompt.sendKeys("Test");
        launchPrompt.accept();
        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: Test");
        Thread.sleep(2000);

        launchPromptButton.click();
        launchPrompt.sendKeys("Test");
        launchPrompt.dismiss();
        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: null");
        Thread.sleep(2000);
    }

    @Test
    @Order(4)
    void launchModalTest() throws InterruptedException {
        WebElement launchModalButton = driver.findElement(By.id("my-modal"));
        launchModalButton.click();
        WebElement saveChanges = driver.findElement(By.xpath("//button[normalize-space() = 'Save changes']"));
        wait.until(ExpectedConditions.elementToBeClickable(saveChanges));
        saveChanges.click();
        Thread.sleep(2000);
        assertThat(driver.findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Save changes");

        launchModalButton.click();
        WebElement close = driver.findElement(By.xpath("//button[text() = 'Close']"));
        wait.until(ExpectedConditions.elementToBeClickable(close));
        close.click();
        assertThat(driver.findElement(By.id("modal-text")).getText()).isEqualTo("You chose: Close");
        Thread.sleep(2000);
    }
}
