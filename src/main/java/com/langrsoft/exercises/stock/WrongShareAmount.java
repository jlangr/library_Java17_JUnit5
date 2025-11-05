package com.langrsoft.exercises.stock;

public class WrongShareAmount extends RuntimeException {
    public WrongShareAmount(String message) {
        super(message);
    }
}
