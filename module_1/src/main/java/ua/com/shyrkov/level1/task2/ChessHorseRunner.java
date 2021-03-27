package ua.com.shyrkov.level1.task2;

import ua.com.shyrkov.factory.ConsoleHelperFactory;
import ua.com.shyrkov.level1.task2.chess.Horse;
import ua.com.shyrkov.service.ConsoleHelperService;

public class ChessHorseRunner {

    private static ConsoleHelperService consoleHelper = ConsoleHelperFactory.getInstance().getHelperService();

    public static void run() {
        consoleHelper.printMessage("------LEVEL 1 - TASK 2------");
        consoleHelper.printMessage("Enter X position of the horse figure:");
        int posX = consoleHelper.readIntegerFromConsole();
        consoleHelper.printMessage("Enter Y position of the horse figure:");
        int posY = consoleHelper.readIntegerFromConsole();
        Horse horse = new Horse(posX, posY);
        consoleHelper.printMessage("Enter X and Y positions of point you want to move:");
        int targetX = consoleHelper.readIntegerFromConsole();
        int targetY = consoleHelper.readIntegerFromConsole();
        if (horse.canMoveToPosition(targetX, targetY)) {
            System.out.println("You can move to this position :)");
        } else System.out.println("You cant move to this position :(");
    }
}
