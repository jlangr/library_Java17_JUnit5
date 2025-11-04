package com.langrsoft.roman;

public class RomanNumber {

    String hardCodedRomanNumbers[] = {
            "I", "V", "X", "L", "C", "D", "M"
    };
    int hardCodedNumbers[] = {
            1, 5, 10, 50, 100, 500, 1000
    };

    public String toRoman(int i) {
        if (i < 1 || i > 1000) {
            throw new IllegalArgumentException("Input must be between 1 and 1000");
        }
        // Find the number in the hard-coded list using a loop
        for (int index = 0; index < hardCodedNumbers.length; index++) {
            if (hardCodedNumbers[index] == i) {
                return hardCodedRomanNumbers[index];
            }
        }

        return null;
    }

}
