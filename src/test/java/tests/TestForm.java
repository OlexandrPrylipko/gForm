package tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import libraries.ReadExcelFile;
import org.junit.Before;
import org.junit.Test;
import pages.FormResponsePage;
import pages.ViewFormPage;
import settings.BaseTest;

import static libraries.Person.*;

public class TestForm extends BaseTest {

    private ViewFormPage viewFormPage;
    private FormResponsePage formResponsePage;

    private static final String MOOD = ReadExcelFile.readData("moodlist.xlsx", "MoodList");

    @Before
    public void setUp() {
        viewFormPage = new ViewFormPage(driver);
        formResponsePage = new FormResponsePage(driver);

        viewFormPage.openPage("/");
    }

    @Test
    @DisplayName("TC-1, TCC-2 - Submitting a form with the correct data")
    @Severity(SeverityLevel.BLOCKER)
    public void submitFormWithCorrectData() {

        viewFormPage.fillInTheForm(EMAIL, BIRTHDAY, NAME, MOOD);

        checkExpectedResult(
                "Form card block is not displayed!",
                formResponsePage.formCardBlockIsDisplayed());
        checkExpectedResult(
                "Confirmation message text is invalid",
                "Ответ записан.",
                formResponsePage.getConfirmationMessageText());
    }

    @Test
    @DisplayName("TC3 - Submitting a form with all fields blank")
    @Severity(SeverityLevel.TRIVIAL)
    public void submitFormWithAllFieldsBlank() {

        viewFormPage.fillInTheForm("", "", "", "");

        checkExpectedResult(
                "Email error message is not displayed",
                viewFormPage.emailErrorMessageIsDisplayed());
        checkExpectedResult(
                "Email error message is invalid",
                "Это обязательный вопрос.",
                viewFormPage.getEmailErrorMessageText());

        checkExpectedResult(
                "Your age error message is not displayed",
                viewFormPage.yourAgeErrorMessageIsDisplayed());
        checkExpectedResult(
                "Your age error message is invalid",
                "Это обязательный вопрос.",
                viewFormPage.getYourAgeErrorMessageText());

        checkExpectedResult(
                "Your name error message is not displayed",
                viewFormPage.yourNameErrorMessageIsDisplayed());
        checkExpectedResult(
                "Your name error message is invalid",
                "Это обязательный вопрос.",
                viewFormPage.getYourNameErrorMessageText());

        checkExpectedResult(
                "Another answer error message is not displayed",
                viewFormPage.anotherAnswerErrorMessageIsDisplayed());
        checkExpectedResult(
                "Another answer error message is invalid",
                "Это обязательный вопрос.",
                viewFormPage.getAnotherAnswerErrorMessageText());
    }

    @Test
    @DisplayName("TC-4 - Submitting a form with an invalid email address")
    @Severity(SeverityLevel.TRIVIAL)
    public void submitFormWithAnInvalidEmail() {

        viewFormPage.fillInTheForm(
                "uvofinderc@gmail",
                BIRTHDAY,
                NAME,
                MOOD);

        checkExpectedResult(
                "Email error message is not displayed",
                viewFormPage.emailErrorMessageIsDisplayed());
        checkExpectedResult(
                "Email error message is invalid",
                "Укажите действительный адрес эл. почты",
                viewFormPage.getEmailErrorMessageText());
    }

    @Test
    @DisplayName("TC-5 - Submitting a form with no existing date")
    @Severity(SeverityLevel.TRIVIAL)
    public void submitFormWithNoExistingDate() {

        viewFormPage.fillInTheForm(
                EMAIL,
                "31.09.2019",
                NAME,
                MOOD);
        viewFormPage.enterTextInTheYourAgeField("31.09.2019");

        checkExpectedResult(
                "Your age error message is not displayed",
                viewFormPage.yourAgeErrorMessageIsDisplayed());
        checkExpectedResult(
                "Your age error message is invalid",
                "Недопустимая дата.",
                viewFormPage.getYourAgeErrorMessageText());
    }

    @Test
    @DisplayName("TC-7 - Submitting a form with a name with more than 20 characters")
    @Severity(SeverityLevel.BLOCKER)
    public void submitFormWithNameMoreThan20characters() {

        viewFormPage.enterTextInTheEmailField(EMAIL);
        viewFormPage.enterTextInTheYourAgeField(BIRTHDAY);
        viewFormPage.enterTextInTheYourNameField("Oleksandr Prylipko");

        checkExpectedResult(
                "Your name error message is not displayed",
                viewFormPage.yourNameErrorMessageIsDisplayed());
        checkExpectedResult(
                "Your name error message is invalid",
                "Текст превышает 20 символов.",
                viewFormPage.getYourNameErrorMessageText());
    }

    @Test
    @DisplayName("TC-8 - Submitting a form with an empty 'Other' field in the 'How is your mood?'")
    @Severity(SeverityLevel.BLOCKER)
    public void submitFormWithEmptyOtherField() {

        viewFormPage.fillInTheForm(
                EMAIL,
                BIRTHDAY,
                NAME,
                "");

        checkExpectedResult(
                "Another answer error message is not displayed",
                viewFormPage.anotherAnswerErrorMessageIsDisplayed());
        checkExpectedResult(
                "Another answer error message is invalid",
                "Это обязательный вопрос.",
                viewFormPage.getAnotherAnswerErrorMessageText());
    }
}