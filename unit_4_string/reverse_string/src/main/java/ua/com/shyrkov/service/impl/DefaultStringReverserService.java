package ua.com.shyrkov.service.impl;

import ua.com.shyrkov.service.StringReverserService;

public class DefaultStringReverserService implements StringReverserService {

    @Override
    public String reverse(String src) {

        String[] wordsOdSource = src.split("\\s");
        StringBuilder resultString = new StringBuilder();
        for (String word : wordsOdSource) {
            char[] charsOfWord = word.toCharArray();
            for (int i = charsOfWord.length - 1; i >= 0; i--) {
                resultString.append(charsOfWord[i]);
            }
            resultString.append(" ");
        }
        return resultString.toString();
    }

    @Override
    public String reverse(String src, String dest) {
        if (src.contains(dest)) {
            String reversedPart = fullReverse(dest);
            return src.replaceAll(dest, reversedPart);
        }
        throw new RuntimeException("Your string doesn't contain substring '" + dest + "'");
    }

    @Override
    public String reverse(String src, int firstIndex, int lastIndex) {
        if (firstIndex >= 0 && lastIndex < src.length() && firstIndex < lastIndex) {
            String substring = src.substring(firstIndex, lastIndex);
            return reverse(src, substring);
        }
        throw new RuntimeException("Wrong first or last index of substring");
    }

    @Override
    public String reverse(String src, char firstChar, char lastChar) {
        return reverse(src, charIndexInString(src, firstChar), charIndexInString(src, lastChar));
    }

    private int charIndexInString(String src, char symbol){
        int index = src.indexOf(symbol);
        if(index>=0){
            return index;
        }
        throw new RuntimeException("There is no such char in string");
    }

    private String fullReverse(String src){
        char[] charArray = src.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = src.length()-1; i >=0 ; i--) {
            builder.append(charArray[i]);
        }
        return builder.toString();
    }
}
