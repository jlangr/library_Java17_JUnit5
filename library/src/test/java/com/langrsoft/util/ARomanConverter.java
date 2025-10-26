package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static com.langrsoft.util.RomanConverter.convert;
import static org.assertj.core.api.Assertions.assertThat;

class ARomanConverter {
   @Test
   void something() {
      assertThat(convert(1)).isEqualTo("I");
      assertThat(convert(2)).isEqualTo("II");
      assertThat(convert(3)).isEqualTo("III");
      assertThat(convert(10)).isEqualTo("X");
      assertThat(convert(30)).isEqualTo("XXX");
      assertThat(convert(22)).isEqualTo("XXII");
      assertThat(convert(213)).isEqualTo("CCXIII");
      assertThat(convert(4)).isEqualTo("IV");
   }

}
