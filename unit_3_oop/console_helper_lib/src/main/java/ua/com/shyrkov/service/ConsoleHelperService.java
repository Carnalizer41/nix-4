package ua.com.shyrkov.service;

import java.io.IOException;
import java.math.BigInteger;

public interface ConsoleHelperService<T extends Number> {

    T readNumberFromConsole();
    void printResult(T result);
    void printMenuOptions();
    void printOperationOptions();
    int readChoice();
    void printMessage(String message);

}
