package com.langrsoft.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ARomanNumberConverter {
   @ParameterizedTest
   @CsvSource({
      "1,I",
      "2,II",
      "3,III",
      "4,IV",
      "5,V",
      "10,X",
      "13,XIII",
      "20,XX",
      "300,CCC",
      "3949,MMMCMXLIX",
      "494,CDXCIV"
   })
   void convertArabicNumbers(int arabic, String expectedRoman) {
      assertThat(new RomanNumberConverter().toRoman(arabic))
         .isEqualTo(expectedRoman);
   }
}
