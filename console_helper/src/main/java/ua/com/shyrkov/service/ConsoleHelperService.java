package ua.com.shyrkov.service;

public interface ConsoleHelperService {

    String readStringFromConsole();
    int readIntegerFromConsole();
    void printMessage(String message);
    char readCharFromConsole();
    double readDoubleFromConsole();

}
