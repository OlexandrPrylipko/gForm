package pages;

import io.qameta.allure.Step;
import libraries.ReadExcelFile;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import settings.BasePage;

public class ViewFormPage extends BasePage {

    private Logger LOG = Logger.getLogger(getClass());

    @FindBy(css = ".exportFormCard")
    private WebElement formCardBlock;

    @FindBy(css = "input[type='email']")
    private WebElement emailField;

    @FindBy(css = "#i2")
    private WebElement emailErrorMessage;

    @FindBy(css = "input[type='date']")
    private WebElement yourAgeField;

    @FindBy(xpath = "//*[text()='Your age: ']/../../../following-sibling::div[2]")
    private WebElement yourAgeErrorMessage;

    @FindBy(css = "input[aria-label='Your name:']")
    private WebElement yourNameField;

    @FindBy(xpath = "//*[text()='Your name: ']/../../../following-sibling::div[2]")
    private WebElement yourNameErrorMessage;

    @FindBy(css = "input[aria-label='Другой ответ']")
    private WebElement anotherAnswerField;

    @FindBy(xpath = "//*[text()='How is your mood? ']/../../../following-sibling::div[3]")
    private WebElement anotherAnswerErrorMessage;

    @FindBy(css = ".freebirdFormviewerViewNavigationSubmitButton")
    private WebElement submitButton;

    public ViewFormPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email address in the 'Email' field")
    public void enterTextInTheEmailField(String email) {
        actionsWithElements.enterTextInToElement(emailField, email);
    }

    public Boolean emailErrorMessageIsDisplayed() {
        return actionsWithElements.isElementPresent(emailErrorMessage);
    }

    public String getEmailErrorMessageText() {
        return actionsWithElements.getElementText(emailErrorMessage).trim();
    }

    @Step("Enter email address in the 'Email' field")
    public void enterTextInTheYourAgeField(String age) {
        actionsWithElements.enterTextInToElement(yourAgeField, age);
    }

    public Boolean yourAgeErrorMessageIsDisplayed() {
        return actionsWithElements.isElementPresent(yourAgeErrorMessage);
    }

    public String getYourAgeErrorMessageText() {
        return actionsWithElements.getElementText(yourAgeErrorMessage).trim();
    }

    @Step("Enter name in the 'Your name' field")
    public void enterTextInTheYourNameField(String name) {
        actionsWithElements.enterTextInToElement(yourNameField, name);
    }

    public Boolean yourNameErrorMessageIsDisplayed() {
        return actionsWithElements.isElementPresent(yourNameErrorMessage);
    }

    public String getYourNameErrorMessageText() {
        return actionsWithElements.getElementText(yourNameErrorMessage).trim();
    }

    private void clickOnMoodCheckbox(String mood) {
        try {
            driver.findElement(By.xpath("//*[contains(@class, 'freebirdFormviewerViewItemsCheckboxLabel') and contains(text(),'" + mood + "')]")).click();
            LOG.info("Selected '" + mood + "' mood");
        } catch (Exception e) {
            LOG.error("The element '" + mood + "' to click is not available: " + e);
            Assert.fail("The element '" + mood + "' to click is not available: " + e);
        }
    }

    @Step("Select mood")
    private void selectMood(String mood) {
        switch (mood) {
            case "Excellent":
            case "Good enough":
            case "Could be better":
            case "Very bad":
                clickOnMoodCheckbox(mood);
                break;
            case "Другое:":
                clickOnMoodCheckbox(mood);
                String otherMood = ReadExcelFile.readData("moodlist.xlsx", "OtherMood");
                actionsWithElements.enterTextInToElement(anotherAnswerField, otherMood);
                LOG.info("Selected other mood: '" + otherMood + "'");
                break;
            default:
                clickOnMoodCheckbox("Другое:");
                actionsWithElements.enterTextInToElement(anotherAnswerField, mood);
                LOG.info("Selected other mood: '" + mood + "'");
        }
    }

    public Boolean anotherAnswerErrorMessageIsDisplayed() {
        return actionsWithElements.isElementPresent(anotherAnswerErrorMessage);
    }

    public String getAnotherAnswerErrorMessageText() {
        return actionsWithElements.getElementText(anotherAnswerErrorMessage).trim();
    }

    @Step("Click on 'Submit' button")
    private void clickOnSubmitButton() {
        actionsWithElements.clickOnElement(submitButton);
    }

    public void fillInTheForm(String email, String age, String name, String mood) {
        enterTextInTheEmailField(email);
        enterTextInTheYourAgeField(age);
        enterTextInTheYourNameField(name);
        selectMood(mood);
        clickOnSubmitButton();
    }
}