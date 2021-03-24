package ua.com.shyrkov.service;

public interface StringReverserService {

    String reverse(String src);
    String reverse(String src, String dest);
    String reverse(String src, int firstIndex, int lastIndex);
    String reverse(String src, char firstChar, char lastChar);

}
