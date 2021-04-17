package ua.com.shyrkov.data;

import lombok.Getter;

@Getter
public enum Month {

    JANUARY(1, "January"),
    FEBRUARY(2, "February"),
    MARCH(3, "March"),
    APRIL(4, "April"),
    MAY(5, "May"),
    JUNE(6, "June"),
    JULY(7, "July"),
    AUGUST(8, "August"),
    SEPTEMBER(9, "September"),
    OCTOBER(10, "October"),
    NOVEMBER(11, "November"),
    DECEMBER(12, "December");

    private int monthNumber;
    private String name;

    Month(int monthNumber, String name) {
        this.monthNumber = monthNumber;
        this.name = name;
    }

    public static Month getByName(String name) {
        for (Month value : Month.values()) {
            if (value.getName().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public static Month getByNumber(int number) {
        for (Month value : Month.values()) {
            if (value.getMonthNumber() == number) {
                return value;
            }
        }
        return null;
    }
}
