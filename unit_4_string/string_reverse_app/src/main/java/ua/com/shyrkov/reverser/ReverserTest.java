package ua.com.shyrkov.reverser;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.service.ConsoleHelperService;
import ua.com.shyrkov.service.StringReverserService;
import ua.com.shyrkov.service.factory.StringReverserFactory;

public class ReverserTest {

    StringReverserService reverserService = StringReverserFactory.getInstance().getReverserService();
    ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public void run() {

        do {
            showOptions();
            int choice = consoleHelper.readIntegerFromConsole();
            chooseOption(choice);
            showMenu();
        } while (consoleHelper.readIntegerFromConsole() != 0);

    }

    private void chooseOption(int choice) {
        switch (choice) {
            case 1:
                doFullReverse();
                break;
            case 2:
                doSubstringReverse();
                break;
            case 3:
                doIndexReverse();
                break;
            case 4:
                doCharReverse();
                break;
            case 0:
                System.exit(0);
            default:
                consoleHelper.printMessage("Wrong option!\nTry again:");
                chooseOption(choice);
        }
    }

    private void doFullReverse() {
        consoleHelper.printMessage("Enter string you want to reverse");
        String string = consoleHelper.readStringFromConsole();
        System.out.println("Result of reversion is: " + reverserService.reverse(string));
    }

    private void doSubstringReverse() {
        consoleHelper.printMessage("Enter string");
        String string = consoleHelper.readStringFromConsole();
        consoleHelper.printMessage("Enter substring you want to reverse");
        String substring = consoleHelper.readStringFromConsole();
        System.out.println("Result of reversion is: " + reverserService.reverse(string, substring));
    }

    private void doIndexReverse() {
        consoleHelper.printMessage("Enter string");
        String string = consoleHelper.readStringFromConsole();
        consoleHelper.printMessage("Enter indexes of substring you want to reverse");
        int firstIndex = consoleHelper.readIntegerFromConsole();
        int lastIndex = consoleHelper.readIntegerFromConsole();
        System.out.println("Result of reversion is: " + reverserService.reverse(string, firstIndex, lastIndex));
    }

    private void doCharReverse() {
        consoleHelper.printMessage("Enter string");
        String string = consoleHelper.readStringFromConsole();
        consoleHelper.printMessage("Enter characters of substring you want to reverse");
        char firstChar = consoleHelper.readCharFromConsole();
        char lastChar = consoleHelper.readCharFromConsole();
        System.out.println("Result of reversion is: " + reverserService.reverse(string, firstChar, lastChar));
    }

    private void showOptions() {
        consoleHelper.printMessage("Choose option: " +
                                           "\nPress 1 to do string full reverse;" +
                                           "\nPress 2 to do string`s substring reverse;" +
                                           "\nPress 3 to do string`s substring reverse with indexes;" +
                                           "\nPress 4 to do string`s substring reverse with characters.");
    }

    private void showMenu() {
        consoleHelper.printMessage("Press 1 to continue operation oe 0 to exit");
    }

}
