package com.langrsoft.roman;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ARomanNumberTest {

    RomanNumber romanNumber = new RomanNumber();
    //1 -> I
    //5 -> V
    //10 -> X
    //50 -? L
    //100 -> C
    //500 -> D
    //1000 -> M
    //4,9, 40, 90, 400, 900 -> prefix testing
    //6try with V -> 6
    //6try with X -> 13
    //6try with L -> 65
    //6try with C -> 125
    //6try with D -> 523
    //6try with VM-> 3123

    @ParameterizedTest
    @CsvSource({
            "1, I",
            "5, V",
            "10, X",
            "50, L",
            "100, C",
            "500, D",
            "1000, M"
    })
    public void testHardCodedRomanNumbers(int input, String expected) {
        assert romanNumber.toRoman(input).equals(expected);
    }

}
