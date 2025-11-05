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

    //Check repetion of nsymbols cases
    //II
    //VV
    //XX

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

    @ParameterizedTest
    @CsvSource({
            "2, II",  //2 to 3 -> 1 to 3
            "30, XXX", // 10 to 39 -> by 10 -> 1 to 3
            "200, DD", //100 to 399 -> by 100 -> 1 to 3
            "3000, MMM", //1000 to 8999 -> by 1000 -> 1 to 3
    })
    public void testHardCodedRomanNumbersUC2(int input, String expected) {
        assert romanNumber.toRoman(input).equals(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "4, IV",  //2 to 3 -> 1 to 3
            "9, IX",  //2 to 3 -> 1 to 3
            "40, XL", // 10 to 39 -> by 10 -> 1 to 3
            "90, XC", //100 to 399 -> by 100 -> 1 to 3
            "400, CD", //1000 to 8999 -> by 1000 -> 1 to 3
            "900, CM", //1000 to 8999 -> by 1000 -> 1 to 3
    })
    public void testHardCodedRomanNumbersUC3(int input, String expected) {
        assert romanNumber.toRoman(input).equals(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "6, VI",  //2 to 3 -> 1 to 3
            "60, LX", // 10 to 39 -> by 10 -> 1 to 3
            "700, DCC", //100 to 399 -> by 100 -> 1 to 3
            "1800, MDCCC", //1000 to 8999 -> by 1000 -> 1 to 3
    })
    public void testHardCodedRomanNumbersUC4(int input, String expected) {
        assert romanNumber.toRoman(input).equals(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "3, VI",  //2 to 3 -> 1 to 3
            "60, LX", // 10 to 39 -> by 10 -> 1 to 3
            "700, DCC", //100 to 399 -> by 100 -> 1 to 3
            "1800, MDCCC", //1000 to 8999 -> by 1000 -> 1 to 3
    })
    public void testHardCodedRomanNumbersUC5(int input, String expected) {
        assert romanNumber.toRoman(input).equals(expected);
    }

}
