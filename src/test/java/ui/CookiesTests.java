package ui;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static steps.WebFormSteps.openCookiesPage;

public class CookiesTests {
    WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/";

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void cookieTest() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(BASE_URL);
        openCookiesPage(driver);

        String textBefore = "username=John Doe\ndate=10/07/2018";
        String textBlock = "username=John Doe\ndate=10/07/2018\nNew President=Donald Trump";
        String textAfter = "username=John Doe\nNew President=Donald Trump";

        WebDriver.Options options = driver.manage();
        WebElement displayCookies = driver.findElement(By.id("refresh-cookies"));
        WebElement cookiesList = driver.findElement(By.id("cookies-list"));
        displayCookies.click();

        assertEquals(textBefore, cookiesList.getText());

        Set<Cookie> cookies = options.getCookies();
        assertThat(cookies).hasSize(2);

        Cookie date = options.getCookieNamed("date");
        assert date != null;
        assertNotNull(date.getValue(), "date.getValue() should be not null");
        assertEquals("10/07/2018", date.getValue());
        assertThat(date.getPath()).isEqualTo("/")
                .as(String.format("date.getPath() should be %s, but was %s", "/", date.getPath()));
        Thread.sleep(2000);

        Cookie addNewCookie = new Cookie("New President", "Donald Trump");
        options.addCookie(addNewCookie);
        displayCookies.click();

        assertEquals(textBlock, cookiesList.getText());

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

        assertEquals(textAfter, cookiesList.getText());
        Thread.sleep(2000);
    }
}
