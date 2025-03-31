package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;

import static org.assertj.core.api.Assertions.assertThat;
import static steps.WebFormSteps.openWebStoragePage;

public class WebStorageTests {
    WebDriver driver;
    WebStorage webStorage;
    LocalStorage localStorage;
    SessionStorage sessionStorage;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openWebStoragePage(driver);
        webStorage = (WebStorage) driver;
        localStorage = webStorage.getLocalStorage();
        sessionStorage = webStorage.getSessionStorage();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void displayLocalStorageTest() throws InterruptedException {
        driver.findElement(By.id("display-local")).click();
        Thread.sleep(2000);
        System.out.printf("Local storage elements: {%s}\n", localStorage.size());

        localStorage.setItem("Name", "Last name");
        assertThat(sessionStorage.size()).isEqualTo(2);
        Thread.sleep(2000);

        driver.findElement(By.id("display-local")).click();
        Thread.sleep(2000);
    }

    @Test
    void displaySessionStorageTest() throws InterruptedException {
        driver.findElement(By.id("display-session")).click();
        Thread.sleep(2000);

        sessionStorage.keySet()
                .forEach(key -> System.out.printf("Session storage: {%s}={%s}\n", key, sessionStorage.getItem(key)));
        assertThat(sessionStorage.size()).isEqualTo(2);
        Thread.sleep(2000);

        sessionStorage.setItem("new element", "new value");
        assertThat(sessionStorage.size()).isEqualTo(3);
        Thread.sleep(2000);

        driver.findElement(By.id("display-session")).click();
        Thread.sleep(2000);
    }
}
