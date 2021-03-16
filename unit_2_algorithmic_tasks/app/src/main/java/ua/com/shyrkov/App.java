package ua.com.shyrkov;

import ua.com.shyrkov.task_1.Task1;
import ua.com.shyrkov.task_2.Task2;
import ua.com.shyrkov.task_3.Lesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter string for task 1...");
        String line1 = readLine(reader);
        System.out.print("Sum of numbers: ");
        System.out.println(Task1.countNumbersInString(line1));

        System.out.println("Enter string for task 2...");
        String line2 = readLine(reader);
        System.out.println("Counting characters...");
        Map<Character, Integer> mapOfCountedChars = Task2.getMapOfCountedChars(line2);
        for (Character character : mapOfCountedChars.keySet()) {
            System.out.println(character + " - " + mapOfCountedChars.get(character));
        }

        System.out.println("Enter lesson number...");
        String line3 = readLine(reader);
        Lesson.getLessonEnding(Integer.parseInt(line3));
    }

    private static String readLine(BufferedReader reader){
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Can`t read line from console");
        }
        return "";
    }
}
