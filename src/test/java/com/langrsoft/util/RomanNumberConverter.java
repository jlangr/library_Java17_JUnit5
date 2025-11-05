package com.langrsoft.util;

import java.util.List;

public class RomanNumberConverter {
   record Conversion(int arabicDigit, String romanDigit) {}

   List<Conversion> conversions = List.of(
      new Conversion(1000, "M"),
      new Conversion(900, "CM"),
      new Conversion(500, "D"),
      new Conversion(400, "CD"),
      new Conversion(100, "C"),
      new Conversion(90, "XC"),
      new Conversion(50, "L"),
      new Conversion(40, "XL"),
      new Conversion(10, "X"),
      new Conversion(9, "IX"),
      new Conversion(5, "V"),
      new Conversion(4, "IV"),
      new Conversion(1, "I")
   );

   public String toRoman(int arabic) {
      var roman = new StringBuilder();
      for (var conversion : conversions) {
         if (arabic >= conversion.arabicDigit) {
            int numberOfDigitsToAppend = arabic / conversion.arabicDigit;
            roman.append(conversion.romanDigit.repeat(numberOfDigitsToAppend));
            arabic -= conversion.arabicDigit * (numberOfDigitsToAppend);
         }
      }
      return roman.toString();
   }
}
