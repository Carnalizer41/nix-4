package ua.com.shyrkov.service;

import ua.com.shyrkov.data.Date;
import ua.com.shyrkov.data.DateFormat;
import ua.com.shyrkov.data.Month;
import ua.com.shyrkov.data.Time;
import ua.com.shyrkov.exception.DateFormatException;

public class DateFormatConverter {

    public static Date convertStringToDate(String input, DateFormat format) throws NullPointerException {
        Date date = new Date();
        Time time = new Time();
        String[] dateTimeParts = input.split(" ");
        String[] datePart = dateTimeParts[0].split(format.getDelimiter());
        String[] timePart = new String[0];
        String[] order = format.getOrder();

        for (int i = 0; i < datePart.length; i++) {
            if (order[i].contains("d")) {
                date.setDay(Integer.parseInt(datePart[i]));
                continue;
            }
            if (order[i].contains("m")) {
                if (format.getMonthFormat().equals("mmm"))
                    date.setMonth(Month.getByName(datePart[i]).getMonthNumber());
                else
                    date.setMonth(Integer.parseInt(datePart[i]));
                continue;
            }
            if (order[i].contains("y")) {
                date.setYear(Integer.parseInt(datePart[i]));
            }
        }

        if (format.getIsTimePresent()) {
            timePart = dateTimeParts[1].split(":");
            if (format.getIsSeconds()) {
                if (format.getIsMillis())
                    time.setMilliseconds(Integer.parseInt(timePart[3]));
                time.setSeconds(Integer.parseInt(timePart[2]));
            }
            time.setMinutes(Integer.parseInt(timePart[1]));
            time.setHours(Integer.parseInt(timePart[0]));
        }
        date.setTime(time);
        return date;
    }

    public static String toString(Date date, DateFormat format) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < format.getOrder().length; i++) {
            String part = format.getOrder()[i];
            switch (part) {
                case DateFormat.DAY_D:
                    builder.append(date.getDay());
                    break;
                case DateFormat.DAY_DD:
                    if (date.getDay() < 10)
                        builder.append(0);
                    builder.append(date.getDay());
                    break;
                case DateFormat.MONTH_M:
                    builder.append(date.getMonth().getMonthNumber());
                    break;
                case DateFormat.MONTH_MM:
                    if (date.getMonth().getMonthNumber() < 10)
                        builder.append(0);
                    builder.append(date.getDay());
                    break;
                case DateFormat.MONTH_MMM:
                    builder.append(date.getMonth().getName());
                    break;
                case DateFormat.YEAR_YY:
                    builder.append(date.getYear()%100);
                    break;
                case DateFormat.YEAR_YYYY:
                    builder.append(date.getYear());
                    break;
                default:
                    throw new DateFormatException("Invalid format of date");
            }
            if (i != format.getOrder().length - 1)
                builder.append(format.getDelimiter());
        }
        builder.append(" ");

        if(format.getIsTimePresent()){
            builder.append(date.getTime().getHours());
            builder.append(":");
            builder.append(date.getTime().getMinutes());
            if(format.getIsSeconds()){
                builder.append(":");
                builder.append(date.getTime().getSeconds());
                if(format.getIsMillis()){
                    builder.append(":");
                    builder.append(date.getTime().getMilliseconds());
                }
            }
        }
        return builder.toString();
    }
}
