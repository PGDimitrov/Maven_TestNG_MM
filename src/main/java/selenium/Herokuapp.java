package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Herokuapp {

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
    public void testAddRemoveElements () throws InterruptedException {

        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        By pageTitle = By.xpath("//h3[contains(text(),'Add/Remove Elements')]");
        By buttonAddElement = By.xpath("//button[contains(text(),'Add Element')]");
        By buttonDelete = By.xpath("//button[contains(text(),'Delete')]");
        By listButtonDelete = By.xpath("//div[@id='elements']/button");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(buttonAddElement).isDisplayed());

        driver.findElement(buttonAddElement).click();
        Assert.assertTrue(driver.findElement(buttonDelete).isDisplayed());

        driver.findElement(buttonDelete).click();

        List<WebElement> listButtonDeletes = driver.findElements(listButtonDelete);
        Assert.assertEquals(listButtonDeletes.size(), 0);

        driver.findElement(buttonAddElement).click();
        driver.findElement(buttonAddElement).click();
        driver.findElement(buttonAddElement).click();


        listButtonDeletes = driver.findElements(listButtonDelete);
        Assert.assertEquals(listButtonDeletes.size(), 3);

        driver.findElement(buttonDelete).click();
        listButtonDeletes = driver.findElements(listButtonDelete);
        Assert.assertEquals(listButtonDeletes.size(), 2);

    }

}
