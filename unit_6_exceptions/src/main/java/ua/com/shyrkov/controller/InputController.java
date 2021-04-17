package ua.com.shyrkov.controller;

import ua.com.shyrkov.data.Date;
import ua.com.shyrkov.data.DateFormat;
import ua.com.shyrkov.exception.DateFormatException;
import ua.com.shyrkov.service.DateFormatConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputController {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private DateFormatConverter converter;

    public Date readDate(DateFormat format) {
        while (true) {
            System.out.println("Please enter date with current format - " + format.toString()+"\n");
            try{
                String line = reader.readLine();
                return converter.convertStringToDate(line, format);

            }catch (IOException | NullPointerException | DateFormatException e){
                System.out.println(e.getMessage());
            }
        }

    }
}
