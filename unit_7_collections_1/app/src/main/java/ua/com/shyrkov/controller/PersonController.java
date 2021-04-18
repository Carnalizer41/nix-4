package ua.com.shyrkov.controller;

import ua.com.shyrkov.OrderedList;
import ua.com.shyrkov.data.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PersonController {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void run() throws IOException {
        List<Person> list = new OrderedList<>();
        while (true) {
            switch (ListOperationController.chooseListOperation()) {
                case 1:
                    list.add(readPerson());
                    break;
                case 2:
                    List<Person> itemsToAdd = new ArrayList<>();
                    System.out.println("Enter amount of Persons to add:");
                    int amountAdd = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < amountAdd; i++) {
                        itemsToAdd.add(readPerson());
                    }
                    list.addAll(itemsToAdd);
                    break;
                case 3:
                    System.out.println("Enter index of Person to remove:");
                    int read = Integer.parseInt(reader.readLine());
                    try {
                        list.remove(read - 1);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("There is no Person with such index:");
                    }
                    break;
                case 4:
                    List<Person> itemsToRemove = new ArrayList<>();
                    System.out.println("Enter amount of Persons to remove:");
                    int amountRemove = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < amountRemove; i++) {
                        itemsToRemove.add(readPerson());
                    }
                    list.removeAll(itemsToRemove);
                    break;
                case 5:
                    List<Person> itemsToRetain = new ArrayList<>();
                    System.out.println("Enter amount of Persons to retain:");
                    int amountRetain = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < amountRetain; i++) {
                        itemsToRetain.add(readPerson());
                    }
                    list.retainAll(itemsToRetain);
                    break;
                case 6:
                    System.out.println("Enter index of Person you want to get:");
                    int index = Integer.parseInt(reader.readLine());
                    System.out.println("Your Person: " + list.get(index - 1));
                    break;
                case 7:
                    System.out.println("Enter Person to check presence in collection:");
                    Person person = readPerson();
                    System.out.println("Presence = " + list.contains(person));
                    break;
                case 8:
                    System.out.println("======Items======");
                    for (Person pers : list) {
                        System.out.print(pers + "; ");
                    }
                    System.out.println();
                    System.out.println("=================");
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    private static Person readPerson() throws IOException {
        while (true) {
            System.out.println("Enter person name:");
            String name = reader.readLine();
            System.out.println("Enter person age:");
            String age = reader.readLine();
            try {
                return new Person(name, Integer.parseInt(age));
            } catch (NumberFormatException e) {
                System.out.println("Wrong input");
            }
        }
    }
}
