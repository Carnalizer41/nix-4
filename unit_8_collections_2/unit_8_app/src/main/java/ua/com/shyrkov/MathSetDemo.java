package ua.com.shyrkov;

import ua.com.shyrkov.math.set.MathSet;
import ua.com.shyrkov.math.set.impl.MathSetImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class MathSetDemo {

    private static MathSet<Integer> set;

    public static void run() {
        init();
        System.out.println("====================");
        join();
        System.out.println("====================");
        sort();
        System.out.println("====================");
        get();
        System.out.println("====================");
        squash();
        System.out.println("====================");
    }

    private static void init() {
        System.out.println("New MathSet 'set' object initialized");
        set = new MathSetImpl<>();
        System.out.println("set.add(1)");
        set.add(1);
        System.out.println("set.add(24)");
        set.add(24);
        System.out.println("set.add(-9)");
        set.add(-9);
        System.out.println("set.add(5, 24, -84)");
        set.add(5, 73, -84);
        System.out.println("Elements in set:");
        printSet(set);
    }

    private static void join() {
        System.out.println("Join() method demo:");
        System.out.println("Creating new MathSet 'secondSet' object");
        MathSet<Integer> secondSet = new MathSetImpl<>();
        System.out.println("secondSet.add(1)");
        secondSet.add(1);
        System.out.println("secondSet.add(37)");
        secondSet.add(37);
        System.out.println("secondSet.add(-13)");
        secondSet.add(-13);
        System.out.println("Elements in set:");
        printSet(set);
        System.out.println("Elements in secondSet:");
        printSet(secondSet);
        System.out.println("Joining secondSet to main set");
        set.join(secondSet);
        System.out.println("Elements in set:");
        printSet(set);
    }

    private static void sort() {
        System.out.println("Sort() methods demo:");
        MathSet<Integer> secondSet = new MathSetImpl<>(set);
        System.out.println("Ascending sorting:");
        secondSet.sortAsc();
        System.out.println("Elements in set:");
        printSet(secondSet);
        secondSet = new MathSetImpl<>(set);
        System.out.println("-----------------------");
        System.out.println("Descending sorting between 2th and 6th indexes");
        secondSet.sortDesc(2, 6);
        System.out.println("Elements in set:");
        printSet(secondSet);

    }

    private static void get() {
        System.out.println("Get() methods demo:");
        System.out.println("set.get(4) - " + set.get(4));
        System.out.println("set.getAverage() - "+set.getAverage());
        System.out.println("set.getMin() - "+set.getMin());
        System.out.println("set.getMax() - "+set.getMax());
        System.out.println("set.getMedian() - "+set.getMedian());
    }

    private static void squash(){
        System.out.println("Squash() method demo:");
        System.out.println("Elements in set:");
        printSet(set);
        System.out.println("Squashing set elements between 2th and 6th indexes:");
        MathSet<Integer> squashed = set.squash(2, 6);
        System.out.println("Elements in set:");
        printSet(squashed);
    }

    private static void printSet(MathSet set) {
        Number[] integers = set.toArray();
        for (Number integer : integers) {
            System.out.print(integer + "\t");
        }
        System.out.println();
    }
}
