package ui;

import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static steps.WebFormSteps.openCookiesPage;

public class CookiesTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @BeforeEach
    void start() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openCookiesPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void cookieTest() throws InterruptedException {
        WebDriver.Options options = driver.manage();
        WebElement displayCookies = driver.findElement(By.id("refresh-cookies"));
        WebElement cookies_list = driver.findElement(By.id("cookies-list"));
        displayCookies.click();

        assertEquals("username=John Doe\n" + "date=10/07/2018", cookies_list.getText());

        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);

        Cookie date = options.getCookieNamed("date");
        assertEquals("10/07/2018", date.getValue());
        assertThat(date.getPath()).isEqualTo("/");
        Thread.sleep(2000);

        Cookie addNewCookie = new Cookie("New President", "Donald Trump");
        options.addCookie(addNewCookie);
        displayCookies.click();

        assertEquals("username=John Doe\n" + "date=10/07/2018\n" + "New President=Donald Trump", cookies_list.getText());

        String readValue = options.getCookieNamed(addNewCookie.getName()).getValue();
        System.out.println(readValue);
        assertThat(addNewCookie.getValue()).isEqualTo(readValue);

        cookies = options.getCookies();
        AssertionsForInterfaceTypes.assertThat(cookies).hasSize(3);
        Thread.sleep(2000);

        options.deleteCookie(date);
        AssertionsForInterfaceTypes.assertThat(options.getCookies()).hasSize(cookies.size() - 1);
        Thread.sleep(2000);

        displayCookies.click();
        assertEquals("username=John Doe\n" + "New President=Donald Trump", cookies_list.getText());
        Thread.sleep(2000);
    }
}
