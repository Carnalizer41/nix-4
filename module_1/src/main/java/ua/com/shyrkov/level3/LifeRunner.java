package ua.com.shyrkov.level3;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.level3.life.LifeGame;
import ua.com.shyrkov.service.ConsoleHelperService;

public class LifeRunner {

    private static final ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public static void run(){
        consoleHelper.printMessage("------LEVEL 3 - TASK 1------");
        consoleHelper.printMessage("Life Game was started!");
        consoleHelper.printMessage("Please enter number of columns of the game board:");
        int columns = consoleHelper.readIntegerFromConsole();
        consoleHelper.printMessage("Please enter number of rows of the game board:");
        int rows = consoleHelper.readIntegerFromConsole();
        consoleHelper.printMessage("Cells of the board were generated randomly");
        LifeGame lifeGame = new LifeGame(columns, rows);
        do {
            consoleHelper.printMessage("---------The next generation of evolution----------");
            lifeGame.calculateNextGeneration();
            consoleHelper.printMessage("Do you want to continue game?\nPress any key to continue or 0 to exit:");
        }while(!consoleHelper.readStringFromConsole().equals("0"));

    }
}
