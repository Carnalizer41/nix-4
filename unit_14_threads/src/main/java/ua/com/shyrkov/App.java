package ua.com.shyrkov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        InThreadWriter writer = new InThreadWriter("src/main/resources/output.txt");
        new Thread(writer).start();
        try {
            System.out.println("Enter text to append and save ('quit' to exit program):");
            while (!(input = reader.readLine()).equals("quit")) {
                writer.appendData(input);
                System.out.println("Enter text to append and save:");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.stop();
    }
}
