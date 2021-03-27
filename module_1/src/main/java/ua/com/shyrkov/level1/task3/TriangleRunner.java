package ua.com.shyrkov.level1.task3;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.level1.task3.triangle.Triangle;
import ua.com.shyrkov.service.ConsoleHelperService;

import java.text.DecimalFormat;

public class TriangleRunner {

    private static final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();
    private static final DecimalFormat f = new DecimalFormat("##.00");

    public static void run(){
        consoleHelper.printMessage("------LEVEL 1 - TASK 3------");
        consoleHelper.printMessage("Enter A side of the triangle:");
        double sideA = consoleHelper.readDoubleFromConsole();
        consoleHelper.printMessage("Enter B side of the triangle:");
        double sideB = consoleHelper.readDoubleFromConsole();
        consoleHelper.printMessage("Enter C side of the triangle:");
        double sideC = consoleHelper.readDoubleFromConsole();
        Triangle triangle = new Triangle(sideA, sideB, sideC);
        consoleHelper.printMessage("Triangle square is:");
        System.out.println(f.format(triangle.calculateSquare()));
    }
}
