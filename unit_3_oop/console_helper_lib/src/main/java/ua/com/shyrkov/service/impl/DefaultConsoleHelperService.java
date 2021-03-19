package ua.com.shyrkov.service.impl;

import ua.com.shyrkov.service.ConsoleHelperService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class DefaultConsoleHelperService implements ConsoleHelperService<BigInteger> {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public DefaultConsoleHelperService() {
        System.out.println("DefaultConsoleHelper.DefaultConsoleHelper");
    }

    @Override
    public BigInteger readNumberFromConsole() {
        try {
            return new BigInteger(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void printResult(BigInteger result) {
        System.out.println("Result of calculation is: " + result);
    }

    @Override
    public void printMenuOptions() {
        System.out.println("Enter '1' to continue calculating or '0' to exit");
    }

    @Override
    public void printOperationOptions() {
        System.out.println("Please, choose operation. Enter: \n" +
                                   "'1' to addition;\n" +
                                   "'2' to subtraction;\n" +
                                   "'3' to multiplying;\n" +
                                   "'4' to division.");
    }

    @Override
    public int readChoice() {
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
