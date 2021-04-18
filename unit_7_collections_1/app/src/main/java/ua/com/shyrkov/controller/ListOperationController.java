package ua.com.shyrkov.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListOperationController {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void run() {
        while (true) {
            switch (chooseListItemType()) {
                case 1:
                    try {
                        IntegerController.run();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        StringController.run();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        PersonController.run();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Wrong option, try again :)");
            }
        }
    }

    private int chooseListItemType() {
        System.out.println("Choose OrderedList items type:\n" +
                                   "1: Integers\n" +
                                   "2: Strings\n" +
                                   "3: Persons (name, age)\n" +
                                   "0: Exit");
        System.out.println("=================");
        String line = "";
        try {
            line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Wrong choose option");
        }
    }

    public static int chooseListOperation() {
        System.out.println("=================");
        System.out.println("Choose operation:\n" +
                                   "1: Add\n" +
                                   "2: Add all\n" +
                                   "3: Remove\n" +
                                   "4: Remove all\n" +
                                   "5: Retain all\n" +
                                   "6: Get object\n" +
                                   "7: Contains object\n" +
                                   "8: Print list\n" +
                                   "0: exit");
        System.out.println("=================");
        try {
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong option");
        }
    }

}
