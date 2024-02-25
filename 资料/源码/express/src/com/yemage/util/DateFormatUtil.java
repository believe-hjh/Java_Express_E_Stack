package com.yemage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yemage
 */
public class DateFormatUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date){
        return simpleDateFormat.format(date);
    }

    public static long toTime(String formatString){
        try {
            return simpleDateFormat.parse(formatString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
