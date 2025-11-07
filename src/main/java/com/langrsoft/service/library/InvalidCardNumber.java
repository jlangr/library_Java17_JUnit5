package com.langrsoft.service.library;

public class InvalidCardNumber extends RuntimeException {
    public InvalidCardNumber(String message) {
        super(message);
    }
}
