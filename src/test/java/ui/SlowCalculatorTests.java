package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static steps.WebFormSteps.openSlowCalculatorPage;

public class SlowCalculatorTests {
    WebDriver driver;
    WebDriverWait wait;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    By zeroButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '0']");
    By oneButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '1']");
    By twoButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '2']");
    By threeButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '3']");
    By fourButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '4']");
    By fiveButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '5']");
    By sixButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '6']");
    By sevenButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '7']");
    By eightButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '8']");
    By nineButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '9']");

    By plusButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '+']");
    By minusButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '-']");
    By divideButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '÷']");
    By multiplyButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = 'x']");
    By equalButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '=']");
    By pointButtonLocator = By.xpath("//div[@class = 'keys']/span[text() = '.']");
    By resultField = By.xpath("//div[@class = 'screen']");

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openSlowCalculatorPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void checkSlowCalcSumTest() {
        String number = "22";
        driver.findElement(fiveButtonLocator).click();
        driver.findElement(plusButtonLocator).click();
        driver.findElement(oneButtonLocator).click();
        driver.findElement(sevenButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        wait.until(ExpectedConditions.textToBe(resultField, number));

        assertThat(driver.findElement(By.xpath("//div[@class = 'screen']")).getText()).isEqualTo(number);
    }

    @Test
    void checkSlowCalcSubtractionTest() {
        String number = "86";
        driver.findElement(oneButtonLocator).click();
        driver.findElement(twoButtonLocator).click();
        driver.findElement(zeroButtonLocator).click();
        driver.findElement(minusButtonLocator).click();
        driver.findElement(threeButtonLocator).click();
        driver.findElement(fourButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        wait.until(ExpectedConditions.textToBe(resultField, number));

        assertThat(driver.findElement(By.xpath("//div[@class = 'screen']")).getText()).isEqualTo(number);
    }

    @Test
    void checkSlowCalcMultiplyTest() {
        String number = "48";
        driver.findElement(sixButtonLocator).click();
        driver.findElement(multiplyButtonLocator).click();
        driver.findElement(eightButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        wait.until(ExpectedConditions.textToBe(resultField, number));

        assertThat(driver.findElement(By.xpath("//div[@class = 'screen']")).getText()).isEqualTo(number);
    }

    @Test
    void checkSlowCalcDivisionTest() {
        String number = "9";
        driver.findElement(eightButtonLocator).click();
        driver.findElement(oneButtonLocator).click();
        driver.findElement(divideButtonLocator).click();
        driver.findElement(nineButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        wait.until(ExpectedConditions.textToBe(resultField, number));

        assertThat(driver.findElement(By.xpath("//div[@class = 'screen']")).getText()).isEqualTo(number);
    }

    @Test
    void checkSlowCalcDoubleTest() {
        String number = "24.5";
        driver.findElement(fiveButtonLocator).click();
        driver.findElement(zeroButtonLocator).click();
        driver.findElement(minusButtonLocator).click();
        driver.findElement(twoButtonLocator).click();
        driver.findElement(fiveButtonLocator).click();
        driver.findElement(pointButtonLocator).click();
        driver.findElement(fiveButtonLocator).click();
        driver.findElement(equalButtonLocator).click();

        wait.until(ExpectedConditions.textToBe(resultField, number));

        assertThat(driver.findElement(By.xpath("//div[@class = 'screen']")).getText()).isEqualTo(number);
    }
}
