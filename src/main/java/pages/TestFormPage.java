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

public class TestFormPage extends BasePage {

    private Logger LOG = Logger.getLogger(getClass());

    @FindBy(css = ".exportFormCard")
    private WebElement formCardBlock;

    @FindBy(css = "input[type='email']")
    private WebElement emailField;

    @FindBy(css = "input[type='date']")
    private WebElement yourAgeField;

    @FindBy(css = "input[aria-label='Your name:']")
    private WebElement yourNameField;

    @FindBy(css = "input[aria-label='Другой ответ']")
    private WebElement anotherAnswerField;

    @FindBy(css = ".freebirdFormviewerViewNavigationSubmitButton")
    private WebElement submitButton;

    public TestFormPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email address in the 'Email' field")
    public void enterTextInTheEmailField(String email) {
        actionsWithElements.enterTextInToElement(emailField, email);
    }

    @Step("Enter email address in the 'Email' field")
    public void enterTextInTheYourAgeField(String age) {
        actionsWithElements.enterTextInToElement(yourAgeField, age);
    }

    @Step("Enter name in the 'Your name' field")
    public void enterTextInTheYourNameField(String name) {
        actionsWithElements.enterTextInToElement(yourNameField, name);
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
    public void selectMood(String mood) {
        switch (mood) {
            case "Excellent":
                clickOnMoodCheckbox(mood);
                break;
            case "Good enough":
                clickOnMoodCheckbox(mood);
                break;
            case "Could be better":
                clickOnMoodCheckbox(mood);
                break;
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

    @Step("Click on 'Submit' button")
    public void clickOnSubmitButton() {
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