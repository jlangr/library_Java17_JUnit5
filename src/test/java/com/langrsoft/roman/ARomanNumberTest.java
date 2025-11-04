package com.langrsoft.roman;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ARomanNumberTest {

    RomanNumber romanNumber = new RomanNumber();
    //Z -> Not supported

    //O
    //1 -> I

    //M
    //2 -> II
    //3 -> III

    //B
    //4000 -> MMMM

    //Error cases
    //<1 or >4000

    //Scenarios check single symbols
    //5 -> V
    //10 -> X
    //50 -? L
    //100 -> C
    //500 -> D
    //1000 -> M

    //Check combinations of symbols
    //55 -> LV
    //15 -> XV
    //200 -> CC
    //600 -> DC
    //1500 -> MD

    //Check 3 combinations of symbols
    //1550 -> MDL
    //210 -> CCX

    //Prefix cases
    //4,9, 40, 90, 400, 900 -> prefix testing

    //Complex cases
    //56 -> LVI
    //94 -> XCIV
    //444 -> CDXLIV
    //944 -> CMXLIV
    //1453 -> MCDLIII
    //3999 -> MMMCMXCIX


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
