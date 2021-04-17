package ua.com.shyrkov.service;

import ua.com.shyrkov.data.DateFormat;
import ua.com.shyrkov.exception.DateFormatException;

public class DateFormatService {

    public static DateFormat setDateFormat(String template){
        DateFormat format = new DateFormat();
        String[] dateTime = template.split(" ");
        String[] dateParts = new String[0];
        String[] timeParts = new String[0];

        if(template.replaceAll("[dm/y0:\\- ]", "").length()>0)
            throw new DateFormatException("Invalid format of date");
        if(!template.contains("d") || !template.contains("m") || !template.contains("y"))
            throw new DateFormatException("Invalid format of date");
        if(template.contains("/")){
            dateParts = dateTime[0].split("/");
            format.setDelimiterSlash(true);
        }
        if(template.contains("-")){
            dateParts = dateTime[0].split("-");
            format.setDelimiterDash(true);
        }
        for (int i = 0; i<dateParts.length; i++) {
            String part = dateParts[i];
            switch (part){
                case DateFormat.DAY_D: format.setDayD(true); format.setOrder(DateFormat.DAY_D, i); break;
                case DateFormat.DAY_DD: format.setDayDD(true); format.setOrder(DateFormat.DAY_DD, i); break;
                case DateFormat.MONTH_M: format.setMonthM(true); format.setOrder(DateFormat.MONTH_M, i); break;
                case DateFormat.MONTH_MM: format.setMonthMM(true); format.setOrder(DateFormat.MONTH_MM, i); break;
                case DateFormat.MONTH_MMM: format.setMonthMMM(true); format.setOrder(DateFormat.MONTH_MMM, i); break;
                case DateFormat.YEAR_YY: format.setYearYY(true); format.setOrder(DateFormat.YEAR_YY, i); break;
                case DateFormat.YEAR_YYYY: format.setYearYYYY(true); format.setOrder(DateFormat.YEAR_YYYY, i); break;
                default: throw new DateFormatException("Invalid format of date");
            }
        }

        if(dateTime.length==2){
            timeParts = dateTime[1].split(":");
            switch (timeParts.length){
                case 4: format.setMillisPresent(true);
                case 3: format.setSecondPresent(true);
                case 2: format.setTimePresent(true); break;
                default: throw new DateFormatException("Invalid format of time");
            }
        }
        return format;
    }
}
