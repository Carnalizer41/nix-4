package ua.com.shyrkov.task2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniqueFinder {

    public static String findUniqueString(List<String> stringList){
        Map<String, Integer> counted = new HashMap<>();
        for (String s : stringList) {
            if(counted.containsKey(s)){
                counted.put(s, counted.get(s)+1);
            }else
                counted.put(s, 1);
        }

        for (String s : counted.keySet()) {
            if(counted.get(s)==1)
                return s;
        }
        return null;
    }
}
