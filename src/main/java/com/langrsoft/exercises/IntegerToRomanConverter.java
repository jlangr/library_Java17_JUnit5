package com.langrsoft.exercises;

public class IntegerToRomanConverter {

    private final int [] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private final String [] literals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String convert(int inputNumber) {
        StringBuilder romanBuilder = new StringBuilder();
        for(int i = 0; i < numbers.length; i++) {
            if(numbers[i] == inputNumber) {
                romanBuilder.append(literals[i]);
                break;
            }
            if (inputNumber > numbers[i]) {
                if (inputNumber % numbers[i] == 0) {
                    int loopCount = inputNumber / numbers[i];
                    for (int j = 0; j < loopCount; j++) {
                        romanBuilder.append(literals[i]);
                    }
                    break;
                }
            }
        }
        return romanBuilder.toString();
    }
}
