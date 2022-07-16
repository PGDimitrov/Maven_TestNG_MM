package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
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

    Actions actions;

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
        actions = new Actions(driver);
    }

    @AfterMethod
    public void tearDown() {

        driver.close();
    }

    @Test
    public void testAddRemoveElements () {

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

    @Test
    public void testBasicAuth () throws InterruptedException {

        driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");

        By pageTitle = By.xpath("//h3[contains(text(),'Basic Auth')]");
        By successfulMessage = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(successfulMessage).isDisplayed());

        Thread.sleep(2000);
    }

    @Test
    public void testChallengingDom () {

        driver.get("https://the-internet.herokuapp.com/challenging_dom");

        By pageTitle = By.xpath("//h3[contains(text(),'Challenging DOM')]");
        By firstParagraph = By.xpath("//p");
        By listButtons = By.xpath("//div[@class='large-2 columns']/a");
        By table = By.xpath("//div[@class='large-10 columns']/table");
        By actionEdit = By.linkText("edit");
        By actionDelete = By.linkText("delete");
        By canvas = By.cssSelector("#canvas");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(firstParagraph).getText().contains("The hardest part in automated web testing is finding the best locators"));
        Assert.assertEquals(driver.findElements(listButtons).size(), 3);
        Assert.assertTrue(driver.findElement(table).isDisplayed());
        Assert.assertTrue(driver.findElement(actionEdit).isDisplayed());
        Assert.assertTrue(driver.findElement(actionDelete).isDisplayed());
        Assert.assertTrue(driver.findElement(canvas).isDisplayed());
    }

    @Test
    public void testCheckboxes () {

        driver.get("https://the-internet.herokuapp.com/checkboxes");

        By pageTitle = By.xpath("//h3[contains(text(),'Checkboxes')]");
        By checkboxesForm = By.cssSelector("#checkboxes");
        By checkboxesList = By.xpath("//form[@id='checkboxes']/input");
        WebElement CheckboxOne = driver.findElements(checkboxesList).get(0);
        WebElement CheckboxTwo = driver.findElements(checkboxesList).get(1);

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(checkboxesForm).isDisplayed());
        Assert.assertTrue(CheckboxOne.isDisplayed());
        Assert.assertFalse(CheckboxOne.isSelected());
        Assert.assertTrue(CheckboxTwo.isDisplayed());
        Assert.assertTrue(CheckboxTwo.isSelected());

        CheckboxOne.click();
        Assert.assertTrue(CheckboxOne.isSelected());

        CheckboxOne.click();
        Assert.assertFalse(CheckboxOne.isSelected());

        CheckboxTwo.click();
        Assert.assertFalse(CheckboxTwo.isSelected());

        CheckboxTwo.click();
        Assert.assertTrue(CheckboxTwo.isSelected());

    }

    @Test
    public void testContextMenu () {

        driver.get("https://the-internet.herokuapp.com/context_menu");

        By pageTitle = By.xpath("//h3[contains(text(),'Context Menu')]");
        By box = By.xpath("//div[@id='hot-spot']");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(box).isDisplayed());

        actions.contextClick(driver.findElement(box)).perform();

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(),"You selected a context menu");
        alert.accept();

    }

    @Test
    public void testDisappearingElements () {

        driver.get("https://the-internet.herokuapp.com/disappearing_elements");

        By pageTitle = By.xpath("//h3[contains(text(),'Disappearing Elements')]");

    }

    @Test
    public void testDragAndDrop () {
        driver.get("https://jqueryui.com/droppable/");

        By pageTitle = By.xpath("//h1[contains(text(),'Droppable')]");
        By dragMe = By.xpath(" //div[@id='draggable']");
        By dropHere = By.xpath("//div[@id='droppable']");
        By textBeforeDrop = By.xpath("//p[contains(text(),'Drop here')]");
        By textAfterDrop = By.xpath("//p[contains(text(),'Dropped')]");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());

        WebElement iFrame = driver.findElement(By.xpath("//iframe"));

        driver.switchTo().frame(iFrame);

        WebElement elementA = driver.findElement(dragMe);
        WebElement elementB = driver.findElement(dropHere);
        WebElement beforeDropText = driver.findElement(textBeforeDrop);


        Assert.assertTrue(elementA.isDisplayed());
        Assert.assertTrue(elementB.isDisplayed());
        Assert.assertTrue(beforeDropText.isDisplayed());

        actions.dragAndDrop(elementA, elementB).perform();

        WebElement afterDropText = driver.findElement(textAfterDrop);
        Assert.assertTrue(afterDropText.isDisplayed());

    }
}
