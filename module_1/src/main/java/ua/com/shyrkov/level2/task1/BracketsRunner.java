package ua.com.shyrkov.level2.task1;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.level2.task1.brackets.Brackets;
import ua.com.shyrkov.level2.task1.brackets.BracketsAnalyzer;
import ua.com.shyrkov.service.ConsoleHelperService;

public class BracketsRunner {

    private static final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public static void run() {
        Brackets[] brackets = {new Brackets('(', ')')
                , new Brackets('{', '}')
                , new Brackets('[', ']')};
        consoleHelper.printMessage("Enter bracket sequence to check is it valid:");
        String sequence = consoleHelper.readStringFromConsole();
        if (BracketsAnalyzer.isBracketsSequenceValid(sequence, brackets)) {
            consoleHelper.printMessage("Bracket sequence is valid :)");
        } else consoleHelper.printMessage("Bracket sequence is not valid :(");
    }
}
