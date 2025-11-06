package com.langrsoft.exercises.roman;

import com.langrsoft.exercises.stock.WrongShareAmount;
import org.junit.Ignore;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanToIntegerTest {
    private final String stringDefault = "anything";

    @Test
    public void zeroAndNegativeRomanNumberTest() {
        var ex1 = assertThrows(WrongInput.class, () -> RomanToInteger.convert(0));
        assertThat(ex1.getMessage()).isEqualTo("Should not be zero or negative");
        var ex2 = assertThrows(WrongInput.class, () -> RomanToInteger.convert(-1));
        assertThat(ex2.getMessage()).isEqualTo("Should not be zero or negative");
    }

    @Test
    public void aboveLimitRomanNumberTest() {

        var ex1 = assertThrows(WrongInput.class, () -> RomanToInteger.convert(401));
        assertThat(ex1.getMessage()).isEqualTo("Should not be above 400");
    }

    @Test
    public void beneathFourRomanNumberTest() {
        assertThat(RomanToInteger.convert(1)).isEqualTo("I");
        assertThat(RomanToInteger.convert(3)).isEqualTo("III");
    }

    @Ignore
    @Test
    public void fourRomanNumberTest() {

        assertThat(RomanToInteger.convert(4)).isEqualTo(stringDefault);
    }

    @Ignore
    @Test
    public void fiveRomanNumberTest() {
        assertThat(RomanToInteger.convert(5)).isEqualTo(stringDefault);
    }

    @Ignore
    @Test
    public void betweenSixAndEightRomanNumberTest() {
        assertThat(RomanToInteger.convert(6)).isEqualTo(stringDefault);
    }

    @Ignore
    @Test
    public void betweenNineAndThirteenRomanNumberTest() {
        assertThat(RomanToInteger.convert(9)).isEqualTo(stringDefault);
        assertThat(RomanToInteger.convert(10)).isEqualTo(stringDefault);
        assertThat(RomanToInteger.convert(11)).isEqualTo(stringDefault);
    }
}
