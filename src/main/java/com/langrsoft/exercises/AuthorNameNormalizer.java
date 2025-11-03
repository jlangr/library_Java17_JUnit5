package com.langrsoft.exercises;


import com.langrsoft.util.InvalidNameException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuthorNameNormalizer {

    // hints:
    // - You might try RegEx replace but things might get ugly...
    // - The String method split is useful.
    // - You might find the code simpler later if you use a LinkedList instead of an array.


    public String normalize(String name) {
        throwWhenExcessCommasExist(name);
        var trimmedName = name.trim();
        var suffix = extractSuffix(trimmedName);
        var baseName = removeSuffix(trimmedName);
        var parts = baseName.split(" ");
        if (isMononym(parts)) return trimmedName;
        if (isDuonym(parts)) return lastName(parts) + ", " + firstName(parts);
        return lastName(parts) + ", " + firstName(parts) + " " + middleInitials(parts) + suffix;
    }

    private void throwWhenExcessCommasExist(String name) {
        if (count(name, ',') > 1) {
            throw new InvalidNameException("name contains 2 commas; maximum is one comma");
        }
    }

    private String removeSuffix(String name) {
        var parts = name.split(",");
        return parts[0];
    }

    private String extractSuffix(String name) {
        var parts = name.split(",");
        if (parts.length == 1) return "";
        return ", " + parts[1].trim();
    }

    private String middleInitials(String[] parts) {
       return Arrays.stream(parts)
          .skip(1)
          .limit(parts.length - 2)
          .map(this::middleInitial)
          .collect(Collectors.joining(" "));
    }

    private String middleInitial(String name) {
        if (name.length() == 1) return name;
        return name.charAt(0) + ".";
    }

    private boolean isDuonym(String[] parts) {
        return parts.length == 2;
    }

    private String firstName(String[] parts) {
        return parts[0];
    }

    private String lastName(String[] parts) {
        return parts[parts.length - 1];
    }

    private boolean isMononym(String[] parts) {
        return parts.length == 1;
    }

    // See http://stackoverflow.com/questions/275944/java-how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
    // ... if you need to convert to < Java 8
   /* private */ long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}

