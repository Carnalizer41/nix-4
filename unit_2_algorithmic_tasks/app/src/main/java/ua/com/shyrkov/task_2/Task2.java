package ua.com.shyrkov.task_2;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Task2 {

    public static Map<Character, Integer> getMapOfCountedChars(String s){
        Map<Character, Integer> resMap = new TreeMap<>();
        char[] chars = s.toCharArray();
        Arrays.sort(chars);

        for (char c : chars) {
            if(Character.isAlphabetic(c)){
                resMap.merge(c, 1, Integer::sum);
            }
        }

        return resMap;
    }
}
