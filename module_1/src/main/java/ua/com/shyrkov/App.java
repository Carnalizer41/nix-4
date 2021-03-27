package ua.com.shyrkov;

import ua.com.shyrkov.level1.task1.CounterRunner;
import ua.com.shyrkov.level1.task2.ChessHorseRunner;
import ua.com.shyrkov.level1.task3.TriangleRunner;
import ua.com.shyrkov.level2.task1.BracketsRunner;
import ua.com.shyrkov.level3.LifeRunner;

public class App {
    public static void main(String[] args) {
        CounterRunner.run();
        ChessHorseRunner.run();
        TriangleRunner.run();
        BracketsRunner.run();
        LifeRunner.run();
    }
}
