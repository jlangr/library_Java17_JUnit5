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
                int afterRemovingBase = inputNumber;
                for(int j = i; j < numbers.length; j++) {
                    while (afterRemovingBase >= numbers[j]) {
                        afterRemovingBase = afterRemovingBase - numbers[j];
                        romanBuilder.append(literals[j]);
                    }
                }
                break;
            }
        }
        return romanBuilder.toString();
    }
}
