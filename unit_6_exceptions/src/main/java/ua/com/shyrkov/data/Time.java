package ua.com.shyrkov.data;

import lombok.Getter;
import ua.com.shyrkov.exception.DateFormatException;

@Getter
public class Time {

    private long milliseconds = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public Time(int hours, int minutes, int seconds, int milliseconds) {
        if (milliseconds > 1000 || milliseconds < 0 || seconds > 60 || seconds < 0 ||
                minutes > 60 || minutes < 0 || hours > 24 || hours < 0)
            throw new DateFormatException("Invalid time format");
        this.milliseconds = milliseconds;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public Time() {
    }

    public long getTimeInMillis() {
        return this.milliseconds + this.seconds * 1000L + this.minutes * 60000L + this.hours * 3600000L;
    }

    public static Time convertMillisToTime(long millis){
        int hours = (int) (millis/3600000L);
        millis -= hours*3600000L;
        int minutes = (int) (millis/60000L);
        millis -= minutes*60000L;
        int seconds = (int) (millis/1000L);
        millis -= seconds*1000L;
        return new Time(hours, minutes, seconds, (int) millis);
    }

    public void setMilliseconds(long milliseconds) {
        if (milliseconds > 1000 || milliseconds < 0)
            throw new DateFormatException("Invalid milliseconds format");
        this.milliseconds = milliseconds;
    }

    public void setSeconds(int seconds) {
        if (seconds > 60 || seconds < 0)
            throw new DateFormatException("Invalid seconds format");
        this.seconds = seconds;
    }

    public void setMinutes(int minutes) {
        if (minutes > 60 || minutes < 0)
            throw new DateFormatException("Invalid minutes format");
        this.minutes = minutes;
    }

    public void setHours(int hours) {
        if (hours > 24 || hours < 0)
            throw new DateFormatException("Invalid hours format");
        this.hours = hours;
    }

    @Override
    public String toString() {
        return this.hours + ":" + this.minutes + ":" + this.seconds + ":" + this.milliseconds;
    }
}
