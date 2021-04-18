package ua.com.shyrkov.controller;

import ua.com.shyrkov.OrderedList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IntegerController {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void run() throws IOException {
        List<Integer> list = new OrderedList<>();
        while (true) {
            switch (ListOperationController.chooseListOperation()) {
                case 1:
                    list.add(readInteger());
                    break;
                case 2:
                    List<Integer> itemsToAdd = new ArrayList<>();
                    System.out.println("Enter amount of items to add:");
                    int amountAdd = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < amountAdd; i++) {
                        itemsToAdd.add(readInteger());
                    }
                    list.addAll(itemsToAdd);
                    break;
                case 3:
                    System.out.println("Enter index of item to remove:");
                    int read = Integer.parseInt(reader.readLine());
                    try {
                        list.remove(read - 1);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("There is no item with such index:");
                    }
                    break;
                case 4:
                    List<Integer> itemsToRemove = new ArrayList<>();
                    System.out.println("Enter amount of items to remove:");
                    int amountRemove = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < amountRemove; i++) {
                        itemsToRemove.add(readInteger());
                    }
                    list.removeAll(itemsToRemove);
                    break;
                case 5:
                    List<Integer> itemsToRetain = new ArrayList<>();
                    System.out.println("Enter amount of items to retain:");
                    int amountRetain = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < amountRetain; i++) {
                        itemsToRetain.add(readInteger());
                    }
                    list.retainAll(itemsToRetain);
                    break;
                case 6:
                    System.out.println("Enter index of item you want to get:");
                    int index = Integer.parseInt(reader.readLine());
                    System.out.println("Your item: " + list.get(index-1));
                    break;
                case 7:
                    System.out.println("Enter number to check presence in collection:");
                    int read1 = Integer.parseInt(reader.readLine());
                    System.out.println("Presence = "+list.contains(read1));
                    break;
                case 8:
                    System.out.println("======Items======");
                    for (Integer integer : list) {
                        System.out.print(integer+"; ");
                    }
                    System.out.println();
                    System.out.println("=================");
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    private static int readInteger() throws IOException {
        while (true) {
            System.out.println("Enter number:");
            String read = reader.readLine();
            try {
                return Integer.parseInt(read);
            } catch (NumberFormatException e) {
                System.out.println("Wrong input");
            }
        }
    }
}
