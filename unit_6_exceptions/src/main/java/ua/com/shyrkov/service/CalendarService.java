package ua.com.shyrkov.service;

import lombok.NonNull;
import ua.com.shyrkov.data.Date;
import ua.com.shyrkov.data.Time;
import ua.com.shyrkov.exception.DateFormatException;

import java.util.*;

import static java.lang.Math.abs;

public class CalendarService {

    private static final int daysInYear = 365;
    private static final int monthsInYear = 12;
    private static final int hoursInDay = 24;
    private static final int minutesInHour = 60;
    private static final int secondsInMinute = 60;
    private static final long millisInSec = 1000L;
    private static final long millisInMinute = 6000L;
    private static final long millisInHour = 3600000L;
    private static final long millisInDay = 86400000L;
    private static final List<Integer> daysInMonth = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);


    public void getDifferenceBetweenDates(@NonNull Date first, @NonNull Date second) {

        long differenceInMillis = abs(getDateInMillis(first) - getDateInMillis(second));
        long years = (long) Math.floor(differenceInMillis / (millisInDay * daysInYear));
        long months = (long) Math.floor(differenceInMillis / (millisInDay * 30));
        long days = (long) Math.floor(differenceInMillis / millisInDay);
        long hours = (long) Math.floor(differenceInMillis / millisInHour);
        long minutes = (long) Math.floor(differenceInMillis / millisInMinute);
        long seconds = (long) Math.floor(differenceInMillis / millisInSec);
        long centuries = years / 100;

        System.out.println("Difference between dates:\n" +
                                   "-in centuries: " + centuries + "\n" +
                                   "-in years: " + years + "\n" +
                                   "-in months: " + months + "\n" +
                                   "-in days: " + days + "\n" +
                                   "-in hours: " + hours + "\n" +
                                   "-in minutes: " + minutes + "\n" +
                                   "-in seconds: " + seconds + "\n" +
                                   "-in milliseconds: " + differenceInMillis + "\n");
    }

    public Date addDate(Date firstDate, Date secondDate) {
        Date newDate = new Date();
        Time newTime = new Time();

        int newMilliseconds = (int) (firstDate.getTime().getMilliseconds() + secondDate.getTime().getMilliseconds());
        newTime.setMilliseconds(newMilliseconds % millisInSec);

        int newSeconds = (int) (firstDate.getTime().getSeconds() + secondDate.getTime()
                                                                             .getSeconds() + (newMilliseconds / millisInSec));
        newTime.setSeconds(newSeconds % secondsInMinute);

        int newMinutes = firstDate.getTime().getMinutes() + secondDate.getTime()
                                                                      .getMinutes() + (newSeconds / secondsInMinute);
        newTime.setMinutes(newMinutes % minutesInHour);

        int newHours = firstDate.getTime().getHours() + secondDate.getTime().getHours() + (newMinutes / minutesInHour);
        newTime.setHours(newHours % hoursInDay);

        int newDays = firstDate.getDay() + secondDate.getDay() + (newHours / hoursInDay);
        int newYear = firstDate.getYear() + secondDate.getYear();
        int newMonth = firstDate.getMonth().getMonthNumber() + secondDate.getMonth().getMonthNumber();

        if (newMonth > monthsInYear) {
            int yearsToAdd = newMonth / monthsInYear;
            for (int i = 1; i <= yearsToAdd; i++) {
                if (isLeapYear(newYear + i)) {
                    newDays -= 1;
                }
                newYear += yearsToAdd;
                newMonth = newMonth % monthsInYear;
            }
        }
        if (newDays <= daysInMonth.get(newMonth - 1)) {
            newDate.setYear(newYear);
            newDate.setMonth(newMonth);
            newDate.setDay(newDays);
        } else {
            while (newDays > daysInMonth.get(newMonth - 1)) {
                if (isLeapYear(newYear) && newMonth == 2)
                    newDays -= 1;
                newDays -= daysInMonth.get(newMonth - 1);
                newMonth += 1;

                if (newMonth > monthsInYear) {
                    newMonth = 1;
                    newYear += 1;
                }
            }
            newDate.setYear(newYear);
            newDate.setMonth(newMonth);
            newDate.setDay(newDays);
        }
        newDate.setTime(newTime);
        return newDate;
    }

    public Date subtractDate(@NonNull Date firstDate, @NonNull Date secondDate) {
        Date newDate = new Date();
        Time newTime = new Time();

        if (getDateInMillis(firstDate) < getDateInMillis(secondDate)) {
            throw new DateFormatException("First date can`t be earlier than second date");
        }

        int millis = (int) (firstDate.getTime().getMilliseconds() - secondDate.getTime().getMilliseconds());
        newTime.setMilliseconds(abs(millis % millisInSec == 0 ? 0 : (millis + millisInSec) % millisInSec));

        int seconds = (int) (firstDate.getTime().getSeconds() - secondDate.getTime()
                                                                          .getSeconds() + millis / millisInSec);
        if (millis < 0)
            seconds--;
        newTime.setSeconds(abs(seconds % secondsInMinute == 0 ? 0 : (seconds + secondsInMinute) % secondsInMinute));

        int minutes = firstDate.getTime().getMinutes() - secondDate.getTime().getMinutes() + seconds / secondsInMinute;
        if (seconds < 0)
            minutes--;
        newTime.setMinutes(abs(minutes % minutesInHour == 0 ? 0 : (minutes + minutesInHour) % minutesInHour));

        int hours = firstDate.getTime().getHours() - secondDate.getTime().getHours() + minutes / minutesInHour;
        if (minutes < 0)
            hours--;
        newTime.setHours(abs(hours % hoursInDay == 0 ? 0 : (hours + hoursInDay) % hoursInDay));

        int days = firstDate.getDay() - secondDate.getDay() + hours / hoursInDay;
        if (hours < 0)
            days--;

        int months = firstDate.getMonth().getMonthNumber() - secondDate.getMonth().getMonthNumber();
        int years = firstDate.getYear() - secondDate.getYear();

        if (months <= 0) {
            int subYears = abs(months / monthsInYear - 1);
            for (int i = 1; i < subYears; i++) {
                if (isLeapYear(years - i))
                    days++;
            }

            years -= subYears;
            months = abs(months % monthsInYear == 0 ? 1 : monthsInYear - (months % monthsInYear));
        }
        if (days > 0) {
            newDate.setYear(years);
            newDate.setDay(days);
            newDate.setMonth(months);
        } else {
            while (days < 0) {
                if (isLeapYear(years) && months == 2)
                    days ++;
                days += daysInMonth.get(months - 1);
                months -= 1;

                if (months <= 0) {
                    months = monthsInYear;
                    years --;
                }
            }
            newDate.setYear(years);
            newDate.setMonth(months);
            newDate.setDay(days);
        }
        return newDate;
    }

    public List<Date> sortDatesByAcs(List<Date> dates){
        dates.sort(Comparator.comparingLong(this::getDateInMillis));
        return dates;
    }

    public List<Date> sortDatesByDesc(List<Date> dates){
        dates.sort((o1, o2) -> Long.compare(getDateInMillis(o2), getDateInMillis(o1)));
        return dates;
    }

    public boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    public long getDateInMillis(Date date) {
        int days = 0;
        for (int i = 0; i < date.getYear(); i++) {
            if (isLeapYear(i))
                days += 366;
            else
                days += 365;
        }
        for (int i = 1; i < date.getMonth().getMonthNumber(); i++) {
            if (i == 2 && isLeapYear(date.getYear())) {
                days += 29;
            } else {
                days += daysInMonth.get(i - 1);
            }
        }
        days += date.getDay();
        return days * millisInDay + date.getTime().getTimeInMillis();
    }
}
