package ua.com.shyrkov.test;

import org.apache.commons.lang3.*;
import org.apache.commons.math3.*;

public class Test {

    public void run(){
        Car car = new Car("Audi", "AX7777XA", "purple");
        System.out.println(StringUtils.upperCase(car.toString()));
    }

}
