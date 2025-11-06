package com.langrsoft.exercises.roman;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {

    private static Map<Integer, String> romanNumbers;

    public static String convert(Integer input) {
        romanNumbers = new HashMap<>();
        romanNumbers.put(1, "I");
        romanNumbers.put(2, "II");
        romanNumbers.put(3, "III");
        //romanNumbers.put("X", 10);

        if (input <= 0) throw new WrongInput("Should not be zero or negative");
        if (input > 400) throw new WrongInput("Should not be above 400");
        if (1 <= input && input <= 3) {
            StringBuilder roman = new StringBuilder();
           return roman.append(romanNumbers.get(input)).toString();
        }


        return "";
    }
}
