package settings;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsWithElements {

    protected WebDriver driver;
    private WebDriverWait wait;
    private Logger LOG = Logger.getLogger(getClass());

    public ActionsWithElements(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    // Click on button, link and other web elements
    public void clickOnElement(WebElement element) {
        String buttonName;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            LOG.error("The element to click is not available: " + e);
            Assert.fail("The element to click is not available: " + e);
        }
    }

    // Enter text in text fields, search fields and other text fields;
    public void enterTextInToElement(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            LOG.info("Text " + "'" + text + "'" + " was input into text field");
        } catch (Exception e) {
            LOG.error("Text field is not available: " + e);
            Assert.fail("Text field is not available: " + e);
        }
    }

    // Checking that the element is displayed on the page;
    public boolean isElementPresent(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            LOG.error("Element is not displayed: " + e);
            Assert.fail("Element is not displayed: ");
            return false;
        }
    }
}