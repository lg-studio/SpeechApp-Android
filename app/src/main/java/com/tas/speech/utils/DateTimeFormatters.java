package com.tas.speech.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * DateTimeFormatters
 */
public class DateTimeFormatters {

    // Method which converts milliseconds to mm:ss format
    public static String convertMillisecondsToMinAndSec(long milliseconds) {
        String format = "mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return sdf.format(calendar.getTime());

    }
}
