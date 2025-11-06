package com.langrsoft.exercises.roman;

import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class RomanToIntegerTest {
    private final String stringDefault = "anything";

    @Test
    public void zeroRomanNumberTest() {
        assertThat(RomanToInteger.convert(0)).isEqualTo(stringDefault);
    }

    @Test
    public void aboveLimitRomanNumberTest() {
        assertThat(RomanToInteger.convert(4001)).isEqualTo(stringDefault);
    }

    @Test
    public void negativeRomanNumberTest() {
        assertThat(RomanToInteger.convert(-1)).isEqualTo(stringDefault);
    }

    @Test
    public void beneathFourRomanNumberTest() {
        assertThat(RomanToInteger.convert(1)).isEqualTo(stringDefault);
        assertThat(RomanToInteger.convert(3)).isEqualTo(stringDefault);
    }

    @Test
    public void fourRomanNumberTest() {
        assertThat(RomanToInteger.convert(4)).isEqualTo(stringDefault);
    }

    @Test
    public void fiveRomanNumberTest() {
        assertThat(RomanToInteger.convert(5)).isEqualTo(stringDefault);

    }

    @Test
    public void betweenSixAndEightRomanNumberTest() {
        assertThat(RomanToInteger.convert(6)).isEqualTo(stringDefault);
    }

    @Test
    public void betweenNineAndThirteenRomanNumberTest() {
        assertThat(RomanToInteger.convert(9)).isEqualTo(stringDefault);
        assertThat(RomanToInteger.convert(10)).isEqualTo(stringDefault);
        assertThat(RomanToInteger.convert(11)).isEqualTo(stringDefault);
    }
}
