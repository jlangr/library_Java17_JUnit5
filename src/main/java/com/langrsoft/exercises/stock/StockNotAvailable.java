package com.langrsoft.exercises.stock;

public class StockNotAvailable extends RuntimeException {
    public StockNotAvailable(String message) {
        super(message);
    }
}
