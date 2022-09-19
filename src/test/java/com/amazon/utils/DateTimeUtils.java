package com.amazon.utils;

import com.amazon.step_def.EGiftCardStepDef;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static Logger logger = LogManager.getLogger(DateTimeUtils.class.getSimpleName());

    public String convertDate(String originalFormatStr, String targetFormatStr, String dateStr) {
        logger.info("About to parse original date: " + dateStr);
        try {
            // "MM/dd/yyyy",
            DateFormat originalFormat = new SimpleDateFormat(originalFormatStr, Locale.ENGLISH);
            // "MMMM dd yyyy"
            DateFormat targetFormat = new SimpleDateFormat(targetFormatStr);
            Date date = originalFormat.parse(dateStr);
            return targetFormat.format(date);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "Couldn't parse date!";
    }
}
