package com.langrsoft.exercises;


public class AuthorNameNormalizer {

    // hints:
    // - You might try RegEx replace but things might get ugly...
    // - The String method split is useful.
    // - You might find the code simpler later if you use a LinkedList instead of an array.


    public String normalize(String name) {
        String retrunname = name.trim();

        String[] names = retrunname.split(" ");

        if (names.length >1){
            retrunname =  names[names.length-1] + ", " + names[0] ;
        }

        if (names.length >2){
            String middleName  = names[1].substring(0,1) + ".";
            retrunname = retrunname  + " " + middleName;
        }

        return retrunname;

    }

    // See http://stackoverflow.com/questions/275944/java-how-do-i-count-the-number-of-occurrences-of-a-char-in-a-string
    // ... if you need to convert to < Java 8
   /* private */ long count(String string, char c) {
        return string.chars().filter(ch -> ch == c).count();
    }
}

