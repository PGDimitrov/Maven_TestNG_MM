package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public class Herokuapp {

    public WebDriver driver;
    public WebDriverWait wait;


    Actions actions;
    JavascriptExecutor executor;

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
        executor = (JavascriptExecutor) driver;
    }

    @AfterMethod
    public void tearDown() {

//        driver.close();
        driver.quit();
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
        Assert.assertTrue(driver.findElement(firstParagraph).getText().contains("The hardest part in automated web testing is finding the best locators (e.g., ones that well named, unique, and unlikely to change). It's more often than not that the application you're testing was not built with this concept in mind. This example demonstrates that with unique IDs, a table with no helpful locators, and a canvas element."));
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
        By listButtons = By.xpath("//ul/li");
        By homeButton = By.xpath("//a[contains(text(),'Home')]");
        By aboutButton = By.xpath("//a[contains(text(),'About')]");
        By contactUsButton = By.xpath("//a[contains(text(),'Contact Us')]");
        By portfolioButton = By.xpath("//a[contains(text(),'Portfolio')]");
        By galleryButton = By.xpath("//a[contains(text(),'Gallery')]");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(listButtons).isDisplayed());

        List<WebElement> listOfButtons = driver.findElements(listButtons);
        if (listOfButtons.size() ==4){
            Assert.assertTrue(driver.findElement(homeButton).isDisplayed());
            Assert.assertTrue(driver.findElement(aboutButton).isDisplayed());
            Assert.assertTrue(driver.findElement(contactUsButton).isDisplayed());
            Assert.assertTrue(driver.findElement(portfolioButton).isDisplayed());
        } else if (listOfButtons.size() ==5) {
            Assert.assertTrue(driver.findElement(homeButton).isDisplayed());
            Assert.assertTrue(driver.findElement(aboutButton).isDisplayed());
            Assert.assertTrue(driver.findElement(contactUsButton).isDisplayed());
            Assert.assertTrue(driver.findElement(portfolioButton).isDisplayed());
            Assert.assertTrue(driver.findElement(galleryButton).isDisplayed());
        }
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

        driver.switchTo().parentFrame();

    }

    @Test
    public void testDropdown () {

        driver.get("https://the-internet.herokuapp.com/dropdown");

        By pageTitle = By.xpath("//h3[contains(text(),'Dropdown List')]");
        By dropdown = By.xpath("//select[@id='dropdown']");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(dropdown).isDisplayed());
        Assert.assertTrue(driver.findElement(dropdown).getText().contains("Please select an option"));

        Select option1dropdown = new Select(driver.findElement(dropdown));
        option1dropdown.selectByVisibleText("Option 1");
        Assert.assertTrue(driver.findElement(dropdown).getText().contains("Option 1"));

        Select option2dropdown = new Select(driver.findElement(dropdown));
        option2dropdown.selectByVisibleText("Option 2");
        Assert.assertTrue(driver.findElement(dropdown).getText().contains("Option 2"));

    }

    @Test
    public void testDynamicContent() {

        driver.get("https://the-internet.herokuapp.com/dynamic_content");

        By pageTitle = By.xpath("//h3[contains(text(),'Dynamic Content')]");
        By buttonClickHere = By.xpath("//a[contains(text(),'click here')]");
        By rowsImages = By.cssSelector("#content img");
        By rowsTexts = By.xpath("//div[@class='large-10 columns']");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(buttonClickHere).isDisplayed());

        List<WebElement> listTextsElements = driver.findElements(rowsTexts);
        List<WebElement> listImagesElements = driver.findElements(rowsImages);

        List<String> listImages = new ArrayList<>();
        for (WebElement element: listImagesElements
             ) {
                listImages.add(element.getAttribute("src"));
        };

        List<String> listTexts = new ArrayList<>();
        for (WebElement element: listTextsElements
        ) {
            listTexts.add(element.getText());
        };

        WebElement clickHere = driver.findElement(buttonClickHere);
        clickHere.click();

        List<WebElement> listTextsAfter = driver.findElements(rowsTexts);
        List<WebElement> listImagesAfter = driver.findElements(rowsImages);

        for (int i = 0; i < listTextsAfter.size(); i++) {
            System.out.println("XXX: " +listTexts.get(i));
            System.out.println("XXX2: " +listTextsAfter.get(i).getText());
            Assert.assertNotEquals(listTexts.get(i), listTextsAfter.get(i).getText());
        }

        for (int i = 0; i < listImagesAfter.size(); i++) {
            System.out.println("YYY: " +listImages.get(i));
            System.out.println("YYY2: " +listImagesAfter.get(i).getAttribute("src"));
            Assert.assertNotEquals(listImages.get(i), listImagesAfter.get(i).getAttribute("src"));
        }
    }

    @Test
    public void testDynamicControls () {

        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        By pageTitle = By.xpath("//h4[contains(text(),'Dynamic Controls')]");
        By checkbox = By.xpath("//div[@id='checkbox']");
        By removeButton = By.xpath("//button[contains(text(),'Remove')]");
        By addButton = By.xpath("//button[contains(text(),'Add')]");
        By checkBoxAdded = By.xpath("//input[@id='checkbox']");
        By enableButton = By.xpath("//button[contains(text(),'Enable')]");
        By disableButton = By.xpath("//button[contains(text(),'Disable')]");
        By inputField = By.xpath("//form[@id='input-example']/input");


        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(checkbox).isDisplayed());
        Assert.assertTrue(driver.findElement(removeButton).isDisplayed());

        driver.findElement(removeButton).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(addButton)));
        driver.findElement(By.xpath("//p[@id='message']")).getText();
        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='message']")).getText(), "It's gone!");
        Assert.assertTrue(driver.findElement(addButton).isDisplayed());

        driver.findElement(addButton).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(removeButton)));
//        driver.findElement(By.xpath("//p[@id='message']")).getText();
        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='message']")).getText(), "It's back!");
        Assert.assertTrue(driver.findElement(checkBoxAdded).isDisplayed());


        Assert.assertTrue(driver.findElement(inputField).isDisplayed());
        Assert.assertTrue(driver.findElement(enableButton).isDisplayed());

        driver.findElement(enableButton).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(disableButton)));
//        driver.findElement(By.xpath("//p[@id='message']")).getText();
        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='message']")).getText(), "It's enabled!");
        Assert.assertTrue(driver.findElement(disableButton).isDisplayed());
        Assert.assertTrue(driver.findElement(inputField).isEnabled());

        driver.findElement(disableButton).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(enableButton)));
        Assert.assertEquals(driver.findElement(By.xpath("//p[@id='message']")).getText(), "It's disabled!");
        Assert.assertTrue(driver.findElement(enableButton).isDisplayed());
        Assert.assertFalse(driver.findElement(inputField).isEnabled());

    }

    @Test
    public void testDynamicLoading () {

        driver.get("https://the-internet.herokuapp.com/dynamic_loading");

        By pageTitle = By.xpath("//h3[contains(text(),'Dynamically Loaded Page Elements')]");

    }

    @Test
    public void testFloatingMenu () {

        driver.get("https://the-internet.herokuapp.com/floating_menu");

        By pageTitle = By.xpath("//h3[contains(text(),'Floating Menu')]");
        By menu = By.xpath("//div[@id='menu']");
        By homeButton = By.xpath("//a[contains(text(),'Home')]");
        By newsButton = By.xpath("//a[contains(text(),'News')]");
        By contactButton = By.xpath("//a[contains(text(),'Contact')]");
        By aboutButton = By.xpath("//a[contains(text(),'About')]");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(menu).isDisplayed());
        Assert.assertTrue(driver.findElement(homeButton).isDisplayed());
        Assert.assertTrue(driver.findElement(newsButton).isDisplayed());
        Assert.assertTrue(driver.findElement(contactButton).isDisplayed());
        Assert.assertTrue(driver.findElement(aboutButton).isDisplayed());

        executor.executeScript("window.scrollBy(0, 5000)");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());
        Assert.assertTrue(driver.findElement(homeButton).isDisplayed());
        Assert.assertTrue(driver.findElement(newsButton).isDisplayed());
        Assert.assertTrue(driver.findElement(contactButton).isDisplayed());
        Assert.assertTrue(driver.findElement(aboutButton).isDisplayed());

        executor.executeScript("window.scrollBy(0, -2000)");
        Assert.assertTrue(driver.findElement(menu).isDisplayed());
        Assert.assertTrue(driver.findElement(homeButton).isDisplayed());
        Assert.assertTrue(driver.findElement(newsButton).isDisplayed());
        Assert.assertTrue(driver.findElement(contactButton).isDisplayed());
        Assert.assertTrue(driver.findElement(aboutButton).isDisplayed());

    }

    @Test
    public void testHovers () {

        driver.get("https://the-internet.herokuapp.com/hovers");

        By pageTitle = By.xpath("//h3[contains(text(),'Hovers')]");
        WebElement figureLeft = driver.findElement(By.xpath("//div[@class='example']/div[1]"));
        By nameUser1 = By.xpath("//h5[contains(text(),'name: user1')]");
        By linkProfileUser1 = By.xpath("//a[@href='/users/1']");

        WebElement figureMiddle = driver.findElement(By.xpath("//div[@class='example']/div[2]"));
        By nameUser2 = By.xpath("//h5[contains(text(),'name: user2')]");
        By linkProfileUser2 = By.xpath("//a[@href='/users/2']");

        WebElement figureRight = driver.findElement(By.xpath("//div[@class='example']/div[3]"));
        By nameUser3 = By.xpath("//h5[contains(text(),'name: user3')]");
        By linkProfileUser3 = By.xpath("//a[@href='/users/3']");

        actions.moveToElement(figureLeft).perform();
        Assert.assertTrue(driver.findElement(nameUser1).isDisplayed());
        Assert.assertTrue(driver.findElement(linkProfileUser1).isDisplayed());
        Assert.assertFalse(driver.findElement(linkProfileUser2).isDisplayed());
        Assert.assertFalse(driver.findElement(linkProfileUser3).isDisplayed());

        actions.moveToElement(figureMiddle).perform();
        Assert.assertTrue(driver.findElement(nameUser2).isDisplayed());
        Assert.assertTrue(driver.findElement(linkProfileUser2).isDisplayed());
        Assert.assertFalse(driver.findElement(linkProfileUser1).isDisplayed());
        Assert.assertFalse(driver.findElement(linkProfileUser3).isDisplayed());

        actions.moveToElement(figureRight).perform();
        Assert.assertTrue(driver.findElement(nameUser3).isDisplayed());
        Assert.assertTrue(driver.findElement(linkProfileUser3).isDisplayed());
        Assert.assertFalse(driver.findElement(linkProfileUser1).isDisplayed());
        Assert.assertFalse(driver.findElement(linkProfileUser2).isDisplayed());

    }

    @Test
    public void testMultipleWindows () {

        driver.get("https://the-internet.herokuapp.com/windows");

        By pageTitle = By.xpath("//h3[contains(text(),'Opening a new window')]");
        By clickHereButton = By.xpath("//a[contains(text(),'Click Here')]");
        By newWindowTitle = By.xpath("//h3[contains(text(),'New Window')]");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(clickHereButton).isDisplayed());

        String originalWindow = driver.getWindowHandle();

        driver.findElement(clickHereButton).click();

//        Set<String> windowsHandles = driver.getWindowHandles();
//        driver.switchTo().window("New Window");
        for (String winHandle : driver.getWindowHandles()
        ){
            driver.switchTo().window(winHandle);
        };

        String newWindow = driver.getWindowHandle();

        Assert.assertTrue(driver.findElement(newWindowTitle).isDisplayed());

        driver.switchTo().window(originalWindow);
        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(clickHereButton).isDisplayed());

        driver.switchTo().window(newWindow);
        Assert.assertTrue(driver.findElement(newWindowTitle).isDisplayed());

    }

    @Test
    public void testRedirectLink () {

        driver.get("https://the-internet.herokuapp.com/redirector");

        By pageTitle = By.xpath("//h3[contains(text(),'Redirection')]");
        By hereRedirectLink = By.xpath("//a[@id='redirect']");

        Assert.assertTrue(driver.findElement(pageTitle).isDisplayed());
        Assert.assertTrue(driver.findElement(hereRedirectLink).isDisplayed());

        String originalURL = driver.getCurrentUrl();
        Assert.assertEquals(originalURL, "https://the-internet.herokuapp.com/redirector");

        driver.findElement(hereRedirectLink).click();
        String redirectedURL = driver.getCurrentUrl();
        Assert.assertEquals(redirectedURL, "https://the-internet.herokuapp.com/status_codes");
        By redirectedPageTitle = By.xpath("//h3[contains(text(),'Status Codes')]");
        Assert.assertTrue(driver.findElement(redirectedPageTitle).isDisplayed());
    }

}
