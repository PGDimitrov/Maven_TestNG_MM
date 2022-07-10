package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class MobileBg {


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
    public void testMobileBg () {

        String carMarka = "Honda";
        String carModel = "Accord";

        driver.get("https://www.mobile.bg/pcgi/mobile.cgi");

        By marka = By.xpath("//select[@name='marka']");
        By model = By.xpath("//select[@name='model']");
        By searchButton = By.xpath("//input[@id='button2']");
        By toTheSite = By.xpath("//p[text()='Към сайта']");

        driver.findElement(toTheSite).click();

        Select markaDropDown = new Select(driver.findElement(marka));
        markaDropDown.selectByVisibleText(carMarka);

        Select modelDropDown = new Select(driver.findElement(model));
        modelDropDown.selectByValue(carModel);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(searchButton)));
        driver.findElement(searchButton).click();

        List<WebElement> listAdd = driver.findElements(By.xpath("//form[@name='search']//a[@class='mmm']"));

        listAdd.forEach(add -> {
            Assert.assertTrue(add.getText().contains(carMarka + " " + carModel));
        });

    }


}
