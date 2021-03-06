package ua.com.shyrkov.test;

public class Car {

    private String model;
    private String number;
    private String color;

    public Car(String model, String number, String color) {
        this.model = model;
        this.number = number;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Model='" + model + '\'' +
                "Number='" + number + '\'' +
                "Color='" + color;
    }
}
