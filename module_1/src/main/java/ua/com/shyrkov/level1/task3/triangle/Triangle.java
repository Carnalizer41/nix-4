package ua.com.shyrkov.level1.task3.triangle;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Setter
@Getter
public class Triangle {

    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double sideA, double sideB, double sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double calculateSquare(){
        double p = calculatePerimeter();
        return Math.sqrt(p*(p-sideA)*(p-sideB)*(p-sideC));
    }

    public double calculatePerimeter(){
        return (sideA+sideB+sideC)/2;
    }
}
