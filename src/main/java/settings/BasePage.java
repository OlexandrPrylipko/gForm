package settings;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

public class BasePage {

    protected WebDriver driver;
    protected ActionsWithElements actionsWithElements;
    private WebDriverWait wait;
    private Logger LOG = Logger.getLogger(getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 15);
        actionsWithElements = new ActionsWithElements(driver);
    }

    @Step("Open page")
    public void openPage(String url) {
        try {
            driver.get(url);
            waitFullLoadPage();
            LOG.info("Website " + getPageUrl() + " was opened");
        } catch (Exception e) {
            LOG.error("The website " + url + " could not opened: " + e);
            Assert.fail("The website " + url + " could not opened: " + e);
        }
    }

    // Get page URL
    public String getPageUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            LOG.error("Page URL is incorrect: " + e);
            Assert.fail("Page URL is incorrect: " + e);
        }
        return null;
    }

    // Waiting until the page is fully loaded
    private void waitFullLoadPage() {
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return String
                        .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });
    }
}