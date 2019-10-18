package libraries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    // Get current time.
    public static String currentDataAndTime() {
        Date dateNow = new Date();
        // Format example yyyyMMddHHmmss: 20190525105355
        // Format example yyyyMMddHHmmssSSS: 20190525105355354
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatForDateNow.format(dateNow);
    }

    // Sleep
    public static void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}