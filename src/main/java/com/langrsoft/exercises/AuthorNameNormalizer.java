package com.langrsoft.exercises;


public class AuthorNameNormalizer {

    // hints:
    // - You might try RegEx replace but things might get ugly...
    // - The String method split is useful.
    // - You might find the code simpler later if you use a LinkedList instead of an array.


    public String normalize(String name) {
        String[] strings = name.trim().split(" ");
        String result = "";
        String middleString = null;
        if (strings.length <= 1) {
            return name;
        }
        if (strings.length > 1) {
            result = strings[strings.length - 1] + ", ";
            result = result + strings[0];
        }
        if (strings.length > 2) {
            for (int i = 1; i < strings.length - 1; i++) {
                middleString = strings[i];
                if (middleString != null) {
                    result = result + " " + middleString.charAt(0) + (middleString.length() > 1 ? "." : "");
                }
            }
        }
        return result;
    }

    // See http://stackoverflow.com/questions/275944/java-how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
    // ... if you need to convert to < Java 8
    /* private */
    long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}

