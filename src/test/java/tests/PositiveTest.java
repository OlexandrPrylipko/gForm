package tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import libraries.ReadExcelFile;
import org.junit.Before;
import org.junit.Test;
import pages.FormResponsePage;
import pages.TestFormPage;
import settings.BaseTest;

import static libraries.Person.*;

public class PositiveTest extends BaseTest {

    private TestFormPage testFormPage;
    private FormResponsePage formResponsePage;

    private static final String MOOD = ReadExcelFile.readData("moodlist.xlsx", "MoodList");

    @Before
    public void setUp() {
        testFormPage = new TestFormPage(driver);
        formResponsePage = new FormResponsePage(driver);
    }

    @Test
    @DisplayName("Send form with the correct data")
    @Severity(SeverityLevel.BLOCKER)
    public void sendFormWithCorrectData() {

        testFormPage.openPage("/");

        testFormPage.fillInTheForm(
                EMAIL,
                BIRTHDAY,
                NAME,
                MOOD);

        checkExpectedResult(
                "Form card block is not displayed!",
                formResponsePage.formCardBlockIsDisplayed());
        checkExpectedResult(
                "Confirmation message text is invalid",
                "Ответ записан.",
                formResponsePage.getConfirmationMessageText());
    }
}