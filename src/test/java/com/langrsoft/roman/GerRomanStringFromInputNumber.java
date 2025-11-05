package com.langrsoft.roman;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GerRomanStringFromInputNumber {
    @Test
    void checkIfItMatchesForValue1() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(1), "I");
    }

    @Test
    void checkIfItMatchesForValue2() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(2), "II");
    }

    @Test
    void checkIfItMatchesForValue4() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(4), "IV");
    }

    @Test
    void checkIfItMatchesForValue10() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(10), "X");
    }

    @Test
    void checkIfItMatchesForValue99() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(99), "XCIX");
    }

    @Test
    void checkIfItMatchesForValue50() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(50), "L");
    }

    @Test
    void checkIfItMatchesForValue49() {
        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(49), "XLIX");
    }

//    @Test
//    void checkIfItMatchesForValue2000() {
//        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
//        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(2000), "MM");
//    }
//
//    @Test
//    void checkIfItMatchesForValue1987() {
//        GetRomanNumberFromNumber GetRomanNumberFromNumber = new GetRomanNumberFromNumber();
//        Assert.assertEquals(GetRomanNumberFromNumber.getRomanString(1987), "MCMLXXXVII");
//    }
}
