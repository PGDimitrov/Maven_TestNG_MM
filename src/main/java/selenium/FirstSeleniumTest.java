package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class FirstSeleniumTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeMethod
    public void setUp() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--window-size=1920x1080");
//        options.addArguments("--headless");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        //Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        //Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {

        driver.close();
    }

    @Test
    public void testLandingPage() throws InterruptedException {

        driver.get("http://training.skillo-bg.com");

        By loginLinkBy = By.id("nav-link-login");
        WebElement loginLink = driver.findElement(loginLinkBy);

        Assert.assertTrue(loginLink.isDisplayed());

        List<WebElement> ListPosts = driver.findElements(By.xpath("//div[@class='row post-list-container']//app-post-detail"));

        Assert.assertEquals(ListPosts.size(), 3);

        loginLink.click();

        WebElement signInTxt = driver.findElement(By.xpath("//p[text()='Sign in']"));

        Assert.assertTrue(signInTxt.isDisplayed());

        Thread.sleep(2000);

    }

    @Test
    public void testLoginPage () throws InterruptedException {

        driver.get("http://training.skillo-bg.com/users/login");

        By userNameBy = By.cssSelector("#defaultLoginFormUsername");
        WebElement userName = driver.findElement(userNameBy);

        By passwordBy = By.cssSelector("#defaultLoginFormPassword");
        WebElement password = driver.findElement(passwordBy);

        By signInButtonBy = By.cssSelector("#sign-in-button");
        WebElement signInButton = driver.findElement(signInButtonBy);

        userName.click();
        userName.clear();
        userName.sendKeys("test01234");
        password.click();
        password.clear();
        password.sendKeys("test43210");
        signInButton.click();

        By profileLink = By.cssSelector("#nav-link-profile");
        Assert.assertTrue(driver.findElement(profileLink).isDisplayed());

        By newPostLink = By.cssSelector("#nav-link-new-post");
        Assert.assertTrue(driver.findElement(newPostLink).isDisplayed());


        By logOutButtonBy = By.cssSelector("i[class='fas fa-sign-out-alt fa-lg']");
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(logOutButton)));
        Assert.assertTrue(driver.findElement(logOutButtonBy).isDisplayed());

        WebElement logoutButton = driver.findElement(logOutButtonBy);
        logoutButton.click();

        Thread.sleep(2000);
    }

    @Test
    public void testProfilePage () throws InterruptedException {

        driver.get("http://training.skillo-bg.com/users/login");

        By userNameBy = By.cssSelector("#defaultLoginFormUsername");
        WebElement userName = driver.findElement(userNameBy);

        By passwordBy = By.cssSelector("#defaultLoginFormPassword");
        WebElement password = driver.findElement(passwordBy);

        By signInButtonBy = By.cssSelector("#sign-in-button");
        WebElement signInButton = driver.findElement(signInButtonBy);

        userName.click();
        userName.clear();
        userName.sendKeys("test01234");
        password.click();
        password.clear();
        password.sendKeys("test43210");
        signInButton.click();

        By profileLinkBy = By.cssSelector("#nav-link-profile");
        WebElement profileLink = driver.findElement(profileLinkBy);

        profileLink.click();

        By profileNameBy = By.xpath("//h2[contains(text(),'test01234')]");
        Assert.assertTrue(driver.findElement(profileNameBy).isDisplayed());

        By profilePicture = By.xpath("//div[@class='image-container']");
        Assert.assertTrue(driver.findElement(profilePicture).isDisplayed());

        By logOutButtonBy = By.cssSelector("i[class='fas fa-sign-out-alt fa-lg']");
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(logOutButton)));
        Assert.assertTrue(driver.findElement(logOutButtonBy).isDisplayed());

        WebElement logoutButton = driver.findElement(logOutButtonBy);
        logoutButton.click();

        Thread.sleep(2000);

    }
}
