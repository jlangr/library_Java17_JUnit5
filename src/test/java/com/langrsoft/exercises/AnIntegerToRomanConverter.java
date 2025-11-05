package com.langrsoft.exercises;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AnIntegerToRomanConverter {
    IntegerToRomanConverter romanConverter = new IntegerToRomanConverter();

    @Test
    void convertOne() {
        assertThat(romanConverter.convert(1)).isEqualTo("I");
    }

    @Test
    void convertFive() {
        assertThat(romanConverter.convert(5)).isEqualTo("V");
    }

    @Test
    void convertTen() {
        assertThat(romanConverter.convert(10)).isEqualTo("X");
    }

    @Test
    void convertFifty() {
        assertThat(romanConverter.convert(50)).isEqualTo("L");
    }

    @Test
    void convertHundred() {
        assertThat(romanConverter.convert(100)).isEqualTo("C");
    }

    @Test
    void convertFiveHundred() {
        assertThat(romanConverter.convert(500)).isEqualTo("D");
    }

    @Test
    void convertOneThousand() {
        assertThat(romanConverter.convert(1000)).isEqualTo("M");
    }

    @Test
    void convertTwo() {
        assertThat(romanConverter.convert(2)).isEqualTo("II");
    }

    @Test
    void convertTwenty() {
        assertThat(romanConverter.convert(20)).isEqualTo("XX");
    }

    @Test
    void convertTwoHundred() {
        assertThat(romanConverter.convert(200)).isEqualTo("CC");
    }

    @Test
    void convertTwoThousand() {
        assertThat(romanConverter.convert(2000)).isEqualTo("MM");
    }

    @Test
    void convertFour() {
        assertThat(romanConverter.convert(4)).isEqualTo("IV");
    }

    @Test
    void convertNine() {
        assertThat(romanConverter.convert(9)).isEqualTo("IX");
    }

    @Test
    void convertForty() {
        assertThat(romanConverter.convert(40)).isEqualTo("XL");
    }

    @Test
    void convertNinety() {
        assertThat(romanConverter.convert(90)).isEqualTo("XC");
    }

    @Test
    void convertFourHundred() {
        assertThat(romanConverter.convert(400)).isEqualTo("CD");
    }

    @Test
    void convertNineHundred() {
        assertThat(romanConverter.convert(900)).isEqualTo("CM");
    }

    @Test
    void convertEightyThree() {
        assertThat(romanConverter.convert(83)).isEqualTo("LXXXIII");
    }
}
