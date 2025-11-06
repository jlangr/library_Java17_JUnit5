package com.langrsoft.exercises.roman;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RomanToInteger {

    public static String convert(Integer input) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<Integer, String> romanNumbers = new LinkedHashMap<>();

        romanNumbers.put(1, "I");
        romanNumbers.put(4, "IV");
        romanNumbers.put(5, "V");

        if (input <= 0) throw new WrongInput("Should not be zero or negative");
        if (input > 400) throw new WrongInput("Should not be above 400");

        if (romanNumbers.get(input) != null ) return romanNumbers.get(input);
        if (input <= 3) {
            for (int i = 1; i <= input; i++){
                stringBuilder.append(romanNumbers.get(1));
            }
            return stringBuilder.toString();
        }

        return "";
    }
}
