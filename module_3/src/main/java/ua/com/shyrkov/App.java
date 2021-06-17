package ua.com.shyrkov;

import checkers.units.quals.C;
import ua.com.shyrkov.controller.Controller;

public class App {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run(args);
    }
}
