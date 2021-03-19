package ua.com.shyrkov.service.impl;

import ua.com.shyrkov.service.CalculatorService;

import java.math.BigInteger;

public class BigIntegerCalculatorService implements CalculatorService<BigInteger> {

    public BigIntegerCalculatorService(){
        System.out.println("DefaultCalculatorService.DefaultCalculatorService");
    }

    @Override
    public BigInteger sum(BigInteger left, BigInteger right) {
        return left.add(right);
    }

    @Override
    public BigInteger subtract(BigInteger left, BigInteger right) {
        return left.subtract(right);
    }

    @Override
    public BigInteger multiply(BigInteger left, BigInteger right) {
        return left.multiply(right);
    }

    @Override
    public BigInteger divide(BigInteger left, BigInteger right) {
        try{
            return left.divide(right);
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Divide by zero!!!");
        }
    }
}
