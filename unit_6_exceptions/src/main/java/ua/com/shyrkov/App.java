package ua.com.shyrkov;

import ua.com.shyrkov.controller.MainController;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        MainController mainController = new MainController();
        while (true) {
            try {
                mainController.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
