package ua.com.shyrkov.controller;

import ua.com.shyrkov.data.Date;
import ua.com.shyrkov.data.DateFormat;
import ua.com.shyrkov.exception.DateFormatException;
import ua.com.shyrkov.service.CalendarService;
import ua.com.shyrkov.service.DateFormatConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final FormatController formatController = new FormatController();
    private final InputController inputController = new InputController();
    private final CalendarService calendarService = new CalendarService();

    public void run() throws IOException {
        DateFormat format = new FormatController().setDefaultFormat();
        System.out.println("Current date format: " + format.toString() +
                                   "\nWhere d - days\nm - months\ny - years\n" +
                                   "'-' or '/' - delimiters\n" +
                                   "00:00:00:00 - time in hours, minutes, seconds, milliseconds\n");
        do {
            format = formatController.changeFormat(format);
            System.out.println("Please choose option:\n" +
                                       "1: Calculate difference between two dates\n" +
                                       "2: Calculate addition of two dates\n" +
                                       "3: Calculate subtraction from date\n" +
                                       "4: Sort dates sequence\n" +
                                       "0: Exit application");
            switch (reader.readLine()) {
                case "1": {
                    try {
                        Date firstDate = inputController.readDate(format);
                        Date secondDate = inputController.readDate(format);
                        calendarService.getDifferenceBetweenDates(firstDate, secondDate);
                    }catch (DateFormatException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "2": {
                    try{
                        Date firstDate = inputController.readDate(format);
                        Date secondDate = inputController.readDate(format);
                        Date res = calendarService.addDate(firstDate, secondDate);
                        System.out.println("Result of addition: "+ DateFormatConverter.toString(res, format));
                    }catch (DateFormatException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "3": {
                    try{
                        Date firstDate = inputController.readDate(format);
                        Date secondDate = inputController.readDate(format);
                        Date res = calendarService.subtractDate(firstDate, secondDate);
                        System.out.println("Result of subtraction: "+ DateFormatConverter.toString(res, format));
                    }catch (DateFormatException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "4": {
                    try{
                        List<Date> dates = new ArrayList<>();
                        do {
                            Date date = inputController.readDate(format);
                            dates.add(date);
                            System.out.println("Enter 0 to stop entering dates or any other key to continue");
                        }while(!reader.readLine().equals("0"));

                        System.out.println("Enter 1 for ascending sorting\n" +
                                                   "Enter 2 for descending sorting");
                        switch (reader.readLine()){
                            case "1":
                                List<Date> ascSortedDates = calendarService.sortDatesByAcs(dates);
                                printDatesList(ascSortedDates, format);
                                break;
                            case "2":
                                List<Date> descSortedDates = calendarService.sortDatesByDesc(dates);
                                printDatesList(descSortedDates, format);
                                break;
                        }
                    }catch (DateFormatException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                default:
                    System.out.println("Wrong option! Please try again");
            }
            System.out.println("Enter 1 to continue or 0 to exit application");
        } while (!reader.readLine().equals("0"));
        System.exit(0);
    }

    private void printDatesList(List<Date> dates, DateFormat format){
        for (Date date : dates) {
            System.out.println(DateFormatConverter.toString(date, format));
        }
    }
}
