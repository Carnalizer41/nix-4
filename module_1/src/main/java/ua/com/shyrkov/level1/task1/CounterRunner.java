package ua.com.shyrkov.level1.task1;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.level1.task1.counter.UniqueNumberCounter;
import ua.com.shyrkov.service.ConsoleHelperService;

public class CounterRunner {

    private static ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public static void run(){
        consoleHelper.printMessage("Enter numbers to count unique:");
        String[] split = consoleHelper.readStringFromConsole().split("\\s+");
        int[] numbers = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            numbers[i] = Integer.parseInt(split[i]);
        }
        consoleHelper.printMessage("Amount of unique numbers in your sequence is:");
        System.out.println(UniqueNumberCounter.countUniqueNumbers(numbers));
    }
}
