package ua.com.shyrkov.data;

import lombok.Getter;
import ua.com.shyrkov.exception.DateFormatException;

@Getter
public class Date {

    private int day = 1;
    private Month month = Month.JANUARY;
    private int year = 0;
    private int century = 1;
    private Time time;
    private boolean isLeap = false;

    public Date() {
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = Month.getByNumber(month);
        this.year = year;
        this.century = year / 100 + 1;
        this.time = new Time();
        setLeap();
    }

    public Date(int day, int month, int year, int hours, int minutes) {
        this.day = day;
        this.month = Month.getByNumber(month);
        this.year = year;
        this.century = year / 100 + 1;
        this.time = new Time(hours, minutes, 0, 0);
        setLeap();
    }

    public Date(int day, int month, int year, int hours, int minutes, int seconds) {
        this.day = day;
        this.month = Month.getByNumber(month);
        this.year = year;
        this.century = year / 100 + 1;
        this.time = new Time(hours, minutes, seconds, 0);
        setLeap();
    }

    public Date(int day, int month, int year, int hours, int minutes, int seconds, int millis) {
        this.day = day;
        this.month = Month.getByNumber(month);
        this.year = year;
        this.time = new Time(hours, minutes, seconds, millis);
        setCentury();
        setLeap();
    }

    public void setYear(int year) {
        if (year < 0)
            throw new DateFormatException("Invalid year");
        this.year = year;
        setCentury();
        setLeap();
    }

    public void setMonth(int month) {
        if (month < 1 || month > 12)
            throw new DateFormatException("Invalid month");
        this.month = Month.getByNumber(month);
    }

    public void setDay(int day) {
        if (day < 1) throw new DateFormatException("Invalid day");
        if (month.getMonthNumber() == 1 || month.getMonthNumber() == 3 ||
                month.getMonthNumber() == 5 || month.getMonthNumber() == 7 ||
                month.getMonthNumber() == 8 || month.getMonthNumber() == 10 || month.getMonthNumber() == 12) {
            if (day > 31) throw new DateFormatException("Invalid day");
            this.day = day;
        }
        if (month.getMonthNumber() == 4 || month.getMonthNumber() == 6 ||
                month.getMonthNumber() == 9 || month.getMonthNumber() == 11) {
            if (day > 30) throw new DateFormatException("Invalid day");
            this.day = day;
        }
        if (month.getMonthNumber() == 2) {
            if (isLeap && day > 29) throw new DateFormatException("Invalid day");
            if (!isLeap && day > 28) throw new DateFormatException("Invalid day");
            this.day = day;
        }
    }

    public void setTime(Time time) {
        this.time = time;
    }

    private void setCentury() {
        this.century = year / 100 + 1;
    }

    private void setLeap() {
        isLeap = year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }


}
