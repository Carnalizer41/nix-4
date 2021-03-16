package ua.com.shyrkov.task_1;

public class Task1 {

    public static int countNumbersInString(String s){
        int sum = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                sum+=Integer.parseInt(Character.toString(c));
            }
        }
        return sum;
    }

}
