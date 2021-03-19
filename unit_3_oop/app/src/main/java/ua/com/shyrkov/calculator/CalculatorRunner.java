package ua.com.shyrkov.calculator;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.service.CalculatorService;
import ua.com.shyrkov.service.ConsoleHelperService;
import ua.com.shyrkov.service.factory.CalculatorFactory;

import javax.lang.model.util.Elements;
import java.math.BigInteger;

public class CalculatorRunner {

    private final CalculatorService calculatorService = CalculatorFactory.getInstance().getCalcService();
    private final ConsoleHelperService consoleHelperService = ConsoleHelperFactory.getInstance().getHelperService();

    public void run() {
        do {
            consoleHelperService.printOperationOptions();
            int choice = consoleHelperService.readChoice();
            chooseOption(choice);
            consoleHelperService.printMenuOptions();
        } while (consoleHelperService.readChoice() != 0);
    }

    private void chooseOption(int choice) {
        switch (choice) {
            case 1:
                calcSum();
                break;
            case 2:
                calcSubtract();
                break;
            case 3:
                calcMultiply();
                break;
            case 4:
                calcDivide();
                break;
            case 0:
                System.exit(0);
            default:
                consoleHelperService.printMessage("Wrong option!\nTry again:");
                chooseOption(choice);
        }
    }

    private void calcSum() {
        consoleHelperService.printMessage("Enter first number:");
        Number left = consoleHelperService.readNumberFromConsole();
        consoleHelperService.printMessage("Enter second number:");
        Number right = consoleHelperService.readNumberFromConsole();
        BigInteger sum = calculatorService.sum(left, right);
        consoleHelperService.printResult(sum);
    }

    private void calcSubtract() {
        consoleHelperService.printMessage("Enter first number:");
        Number left = consoleHelperService.readNumberFromConsole();
        consoleHelperService.printMessage("Enter second number:");
        Number right = consoleHelperService.readNumberFromConsole();
        BigInteger subtract = calculatorService.subtract(left, right);
        consoleHelperService.printResult(subtract);
    }

    private void calcMultiply() {
        consoleHelperService.printMessage("Enter first number:");
        Number left = consoleHelperService.readNumberFromConsole();
        consoleHelperService.printMessage("Enter second number:");
        Number right = consoleHelperService.readNumberFromConsole();
        BigInteger multiply = calculatorService.multiply(left, right);
        consoleHelperService.printResult(multiply);
    }

    private void calcDivide() {
        consoleHelperService.printMessage("Enter first number:");
        Number left = consoleHelperService.readNumberFromConsole();
        consoleHelperService.printMessage("Enter second number:");
        Number right = consoleHelperService.readNumberFromConsole();
        BigInteger divide = calculatorService.divide(left, right);
        consoleHelperService.printResult(divide);
    }
}
