package com.langrsoft.util;

import java.util.List;

public class RomanConverter {
   record Conversion(int arabic, String roman) {}

   private static List<Conversion> conversions = List.of(
      new Conversion(100, "C"),
      new Conversion(10, "X"),
      new Conversion(4, "IV"),
      new Conversion(1, "I")
   );

   static String convert(int arabic) {
      var roman = "";
      for (var conversion: conversions) {
         while (arabic >= conversion.arabic) {
            roman += conversion.roman;
            arabic -= conversion.arabic;
         }
      }
      return roman;
   }
}
