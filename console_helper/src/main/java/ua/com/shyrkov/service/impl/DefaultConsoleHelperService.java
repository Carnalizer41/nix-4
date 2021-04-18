package ua.com.shyrkov.service.impl;


import ua.com.shyrkov.service.ConsoleHelperService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DefaultConsoleHelperService implements ConsoleHelperService {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public String readStringFromConsole() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public int readIntegerFromConsole() {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public void printMessage(String message) {
        System.out.println(message);
    }


    public char readCharFromConsole() {
        try {
            String line = reader.readLine();
            if(line.length()==1){
                return line.toCharArray()[0];
            }
            throw new RuntimeException("You entered more than one character");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    public double readDoubleFromConsole() {
        try {
            return Double.parseDouble(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
