package ua.com.shyrkov.level2.task1.brackets;

import java.util.Stack;

public class BracketsAnalyzer {

    public static boolean isBracketsSequenceValid(String sequence, Brackets[] validBracketsArray){
        Stack<Character> bracketsStack = new Stack<>();
        for (char c : sequence.toCharArray()) {
            for (Brackets bracket : validBracketsArray) {
                if(c == bracket.getOpen()){
                    bracketsStack.add(c);
                    break;
                }else if(c == bracket.getClose()){
                    if(bracketsStack.peek()==bracket.getOpen()){
                        bracketsStack.pop();
                        break;
                    }else
                        return false;
                }
            }
        }
        return bracketsStack.isEmpty();
    }
}
