package ua.com.shyrkov.level1.task1.counter;

import java.util.HashSet;
import java.util.Set;

public class UniqueNumberCounter {

    public static int countUniqueNumbers(int[] numbersArray){
        Set<Integer> integerSet = new HashSet<>();
        for (int i : numbersArray) {
            integerSet.add(i);
        }
        return integerSet.size();
    }

}
