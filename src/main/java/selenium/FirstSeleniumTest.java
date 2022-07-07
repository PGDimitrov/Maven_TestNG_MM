package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FirstSeleniumTest {

    public WebDriver driver;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--window-size=1920x1080");
//        options.addArguments("--headless");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {

        driver.close();
    }

    @Test
    public void firstSeleniumTest() throws InterruptedException {

        driver.get("http://training.skillo-bg.com");

        By loginLinkBy = By.id("nav-link-login");
        WebElement loginLink = driver.findElement(loginLinkBy);

        loginLink.click();

        WebElement signInTxt = driver.findElement(By.xpath("//p[text()='Sign in']"));

        Assert.assertTrue(signInTxt.isDisplayed());

//        Thread.sleep(2000);

    }
}
