package ua.com.shyrkov.level2.task1.brackets;

public class Brackets {

    private final char open;
    private final char close;

    public Brackets(char open, char close) {
        this.open = open;
        this.close = close;
    }

    public char getOpen() {
        return open;
    }

    public char getClose() {
        return close;
    }
}
