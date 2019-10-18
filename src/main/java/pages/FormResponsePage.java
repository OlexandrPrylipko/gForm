package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import settings.BasePage;

public class FormResponsePage extends BasePage {

    @FindBy(css = ".exportFormCard")
    private WebElement formCardBlock;

    @FindBy(css = ".freebirdFormviewerViewResponseConfirmationMessage")
    private WebElement confirmationMessageText;

    public FormResponsePage(WebDriver driver) {
        super(driver);
    }

    public Boolean formCardBlockIsDisplayed() {
        return actionsWithElements.isElementPresent(formCardBlock);
    }

    public String getConfirmationMessageText() {
        return actionsWithElements.getElementText(confirmationMessageText);
    }
}