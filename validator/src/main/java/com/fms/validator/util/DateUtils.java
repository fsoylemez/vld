package com.fms.validator.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fatih.soylemez on 9/19/2016.
 */
public class DateUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public static Date toDate(String dateString){
        if(dateString==null || dateString.equals(""))
            return null;
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
