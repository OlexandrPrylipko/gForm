package libraries;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static libraries.Utilities.currentDataAndTime;

public class Person {

    public static final String EMAIL = "uvofinderc+" + currentDataAndTime() + "@gmail.com";
    public static final String BIRTHDAY = getBirthdayDate();
    public static final String NAME = getName();

    private static String getBirthdayDate() {

        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        int year = randBetween(1950, 2015);
        gregorianCalendar.set(Calendar.YEAR, year);

        int dayOfYear = randBetween(1, gregorianCalendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        gregorianCalendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        return gregorianCalendar.get(Calendar.DAY_OF_MONTH) + "." +
                (gregorianCalendar.get(Calendar.MONTH) + 1) + "." +
                gregorianCalendar.get(Calendar.YEAR);
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private static String getName() {
        return ReadExcelFile.readData("namelist.xlsx", "NameList");
    }
}