package com.langrsoft.exercises.roman;

public class RomanToInteger {

    public static String convert(Integer input) {
        if (input <= 0) throw new WrongInput("Should not be zero or negative");
        if(input > 4000) throw new WrongInput("Should not be above 4000");

        return "";
    }
}
