package com.langrsoft.exercises;

import com.langrsoft.util.InvalidNameException;
import java.util.Arrays;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

public class AuthorNameNormalizer {
   public String normalize(String name) {
      validateSuffixes(name);
      name = name.trim();
      var suffix = extractSuffix(name);
      var baseName = extractBaseName(name);
      var parts = baseName.split(" ");
      if (isMononym(parts)) return name;
      if (isDuonym(parts))
         return String.format("%s, %s", last(parts), first(parts));
      return String.format("%s, %s %s%s", last(parts), first(parts), middleInitial(parts), formatSuffix(suffix));
   }

   private void validateSuffixes(String name) {
      if (count(name, ',') > 1)
         throw new InvalidNameException("name contains 2 commas; maximum is one comma");
   }

   private String formatSuffix(String suffix) {
      return suffix == null ? "" : ", " + suffix;
   }

   private String extractBaseName(String name) {
      return name.split(",")[0];
   }

   private String extractSuffix(String name) {
      var parts = name.split(",");
      return parts.length == 1 ? null : parts[1].trim();
   }

   private String middleInitial(String[] parts) {
      return middleNames(parts)
         .map(this::initialize)
         .collect(joining(" "));
   }

   private Stream<String> middleNames(String[] parts) {
      return Arrays.stream(parts)
         .skip(1)
         .limit(parts.length - 2);
   }

   private String initialize(String name) {
      return name.length() == 1
         ? name
         : name.charAt(0) + ".";
   }

   private boolean isDuonym(String[] parts) {
      return parts.length == 2;
   }

   private String first(String[] parts) {
      return parts[0];
   }

   private String last(String[] parts) {
      return parts[parts.length - 1];
   }

   private boolean isMononym(String[] parts) {
      return parts.length == 1;
   }

   private long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}

