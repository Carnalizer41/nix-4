package ua.com.shyrkov.task1.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class DateParser {

    private String[] dateFormats;

    public DateParser() {
        this.dateFormats = new String[]{"uuuu/MM/dd", "dd/MM/uuuu", "MM-dd-uuuu"};
    }

    public String parse(String input) {
        if (isValid(input, dateFormats[0])) {
            String[] split = input.split("/");
            return split[0] + split[1] + split[2];
        } else if (isValid(input, dateFormats[1])) {
            String[] split = input.split("/");
            return split[2] + split[1] + split[0];
        } else if (isValid(input, dateFormats[2])) {
            String[] split = input.split("-");
            return split[2] + split[0] + split[1];
        }
        return null;
    }

    private boolean isValid(String date, String format) {

        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(format));

            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
