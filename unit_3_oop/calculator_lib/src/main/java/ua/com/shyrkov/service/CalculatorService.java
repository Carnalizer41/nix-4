package ua.com.shyrkov.service;

import java.math.BigInteger;

public interface CalculatorService<T extends Number> {

    BigInteger sum(T left, T right);
    BigInteger subtract(T left, T right);
    BigInteger multiply(T left, T right);
    BigInteger divide(T left, T right);


}
