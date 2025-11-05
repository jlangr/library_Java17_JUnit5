package com.langrsoft.exercises;

public class IntegerToRomanConverter {

    private final int [] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private final String [] literals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String convert(int inputNumber) {
        StringBuilder romanBuilder = new StringBuilder();

        //3784- 3000 + 700 + 80 + 4  ->  1000 + 1000 + 1000 + 500 + 100 +100 + 50 + 10 +10 + 10 +4

        int afterRemovingBase = inputNumber;
        for(int j = 0; j < numbers.length; j++) {
            while (afterRemovingBase >= numbers[j]) {
                afterRemovingBase = afterRemovingBase - numbers[j];
                romanBuilder.append(literals[j]);
            }
        }
        return romanBuilder.toString();
    }
}
