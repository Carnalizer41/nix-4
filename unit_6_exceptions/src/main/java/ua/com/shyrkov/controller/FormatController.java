package ua.com.shyrkov.controller;

import ua.com.shyrkov.data.DateFormat;
import ua.com.shyrkov.exception.DateFormatException;
import ua.com.shyrkov.service.DateFormatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FormatController {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public DateFormat changeFormat(DateFormat format) throws IOException {
        while (true) {
            System.out.println("Do you wanna change date format?\nEnter 1 to set format or 0 to continue");
            switch (reader.readLine()) {
                case "1":
                    try {
                        return readFormat();
                    } catch (IOException | DateFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "0":
                    return format;
            }
        }
    }

    private DateFormat readFormat() throws IOException {
        System.out.println("Please enter date format using next symbols:\n" +
                                   "d - days\nm - months\ny - years\n'-' or '/' - delimiters\n00:00:00:00 - time in hours, minutes, seconds, milliseconds\n" +
                                   "==========\n" +
                                   "ex. d-m-yy 00:00  =  1-1-99 19:30\n" +
                                   "    dd/mm/yy 00:00:00  =  01/01/99 19:30:52\n" +
                                   "    dd/mmm/yyyy 00:00:00:00  =  01/January/1999 19:30:52:01");
        String line = reader.readLine();
        return DateFormatService.setDateFormat(line);
    }

    public DateFormat setDefaultFormat() {
        DateFormat format = new DateFormat();
        format.setDefaultFormat();
        return format;
    }
}
