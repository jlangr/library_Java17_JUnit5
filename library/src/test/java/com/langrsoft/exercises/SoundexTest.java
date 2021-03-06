package com.langrsoft.exercises;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//Implementing the Soundex Algorithm--Test-First
//
//Per Wikipedia, Soundex is a phonetic algorithm for indexing names
//by sound, as pronounced in English. The goal is for homophones to
//be encoded to the same representation so that they can be matched
//despite minor differences in spelling.
//
//Each @Test method in SoundexTest.java describes a small increment
//of behavior needed to build the Soundex algorithm.
//
// First: Uncomment the test source
//
// For each test from top to bottom:
//- Remove the @Disabled("uncomment when ready") annotation
//- Run all tests. Ensure the newly-unignored (current) test fails,
//  and that all other previously passing tests still pass.
//- Write code in Soundex.java to get the current test to pass.
//  Build no more implementation than required.
//
//You'll find a couple very useful helper methods in Soundex.java.
//Use these to allow you to focus on incrementing the core algorithm.
//
//If you un-ignore a test, and it immediately passes,
//you wrote too much code to get a prior test to pass.
//Return to the prior step and find a way to get prior tests
//passing with the minimal code needed.
//
//Read additional comments in each test and follow any additional rules specified.
//
//The rules for Soundex, per Wikipedia (http://en.wikipedia.org/wiki/Soundex):
//1. Retain the first letter of the name and drop all other occurrences of a,e,i,o,u,y,h,w.
//2. Replace consonants with digits as follows (after the first letter):
//    b, f, p, v => 1
//    c, g, j, k, q, s, x, z => 2
//    d, t => 3
//    l => 4
//    m, n => 5
//    r => 6
//3. Two adjacent letters (in the original name) with the same number are coded as a single
//   number; also two letters with the same number separated by 'h' or 'w' are coded as a
//   single number, whereas such letters separated by a vowel are coded twice. This rule
//   also applies to the first letter.
//4. Continue until you have one letter and three numbers. If you run out of letters, fill
//   in 0s until there are three numbers.


class SoundexTest {
    private Soundex soundex;

    @BeforeEach
    void create() {
        soundex = new Soundex();
        soundex.encode("X"); // delete me when ready
    }

    @Test
    void loadsLookupTable() {
        assertThat(soundex.digits.get('b'), equalTo('1'));
    }

    @Test
    void isVowelReturnsTrueForLegitimateVowel() {
        assertThat(soundex.isVowel('e'), is(true));
    }

    @Test
    void isVowelReturnsFalseForNonVowel() {
        assertThat(soundex.isVowel('x'), is(false));
    }

    @Test
    void isVowelReturnsTrueForLegitimateVowelLike() {
        assertThat(soundex.isVowelLike('h'), is(true));
    }

    @Test
    void isVowelReturnsFalseForNonVowelLike() {
        assertThat(soundex.isVowelLike('k'), is(false));
    }

    @Test
    @Disabled("uncomment when ready")
    void retainsSoleLetterOfOneLetterWord() {
        assertThat(soundex.encode("A"), equalTo("A000"));
    }

    @Test
    @Disabled("uncomment when ready")
    void replacesConsonantsWithAppropriateDigits() {
        assertThat(soundex.encode("Ab"), equalTo("A100"));
        // Uncomment each subsequent assertion, and get to pass, one at a time:
        // assertThat(soundex.encode("Bcdl"), is(equalTo("B234")));
        // assertThat(soundex.encode("Ajmr"), is(equalTo("A256")));

        // Prepare to discuss: Should we have multiple assertions in one test?
    }

    @Test
    @Disabled("uncomment when ready")
    void limitsLengthToFourCharacters() {
        assertThat(soundex.encode("Dbcdlmr"), equalTo("D123"));
    }

    @Test
    @Disabled("uncomment when ready")
    void ignoresVowelLikeLetters() {
        assertThat(soundex.encode("Faeiouhycts"), equalTo("F232"));
    }

    @Test
    @Disabled("uncomment when ready")
    void combinesDuplicateEncodingsAsSingleNumber() {
        // Prepare to discuss:
        // - What is the value of these three potential preconditions?
        // - What is the cost (tradeoff)? How do you feel about the design choice?
        // assertThat soundex.toDigit('b') equalTo soundex.toDigit('f')
        // assertThat soundex.toDigit('c') equalTo soundex.toDigit('g')
        // assertThat soundex.toDigit('d') equalTo soundex.toDigit('t')

        assertThat(soundex.encode("Gbfcgdt"), equalTo("G123"));
    }

    @Test
    @Disabled("uncomment when ready")
    void uppercasesFirstLetter() {
        assertThat(soundex.encode("abcd"), equalTo("A123"));
    }

    @Test
    @Disabled("uncomment when ready")
    void ignoresVowelLikeLettersRegardlessOfCase() {
        assertThat(soundex.encode("FcAEIOUHYts"), equalTo("F232"));
    }

    @Test
    @Disabled("uncomment when ready")
    void replacesConsonantsWithAppropriateDigitsRegardlessOfCase() {
        assertThat(soundex.encode("BCDL"), equalTo("B234"));
    }

    @Test
    @Disabled("uncomment when ready")
    void combinesDuplicateEncodingsWhenSecondLetterDuplicatesFirst() {
        assertThat(soundex.encode("Bbcd"), equalTo("B230"));
    }

    @Test
    @Disabled("uncomment when ready")
    void doesNotCombineDuplicateEncodingsSeparatedByVowels() {
        assertThat(soundex.encode("Jbobby"), equalTo("J110"));
    }
    // Congratulations if you made it this far!
    // Prepare to discuss:
    // - What other tests are missing?
    // - What were the costs and benefits of building Soundex incrementally?
}
