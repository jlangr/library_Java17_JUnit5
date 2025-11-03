package com.langrsoft.exercises;

// 0. Uncomment the whole thing.
// 1. Un-ignore the next commented-out test method.
// 2. Run JUnit against *all* tests in the project.
// 3. Did the current test fail? If not: You built too much code in a prior step. Undo work for prior tests and try again.
// 4. Make sure you are clear on why the test failed.
// 5. Write only enough code to make that failing test pass (and not break any other tests).
//    Did you write too much? Is there a simple way to get that test to pass???
// 6. If there is a commented-out assertion, uncomment it. It should fail. If not, return to step 5.
// 7. Return to step 1.


import com.langrsoft.util.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnAuthorNameNormalizer {
   private AuthorNameNormalizer normalizer;

   @BeforeEach
   void create() {
      normalizer = new AuthorNameNormalizer();
   }

   @Test
   void returnsEmptyStringWhenEmpty() {
      assertThat(normalizer.normalize("")).isEqualTo("");
   }

   @Test
   void returnsSingleWordName() {
      assertThat(normalizer.normalize("Plato")).isEqualTo("Plato");
   }

   @Disabled
   @Test
   void returnsLastFirstWhenFirstLastProvided() {
      assertThat(normalizer.normalize("Haruki Murakami")).isEqualTo("Murakami, Haruki");
   }

   @Disabled
   @Test
   void trimsLeadingAndTrailingWhitespace() {
      assertThat(normalizer.normalize("  Big Boi   ")).isEqualTo("Boi, Big");
   }

   @Disabled
   @Test
   void initializesMiddleName() {
      assertThat(normalizer.normalize("Henry David Thoreau")).isEqualTo("Thoreau, Henry D.");
   }

   @Disabled
   @Test
   void doesNotInitializeOneLetterMiddleName() {
      assertThat(normalizer.normalize("Harry S Truman")).isEqualTo("Truman, Harry S");
   }

   @Disabled
   @Test
   void initializesEachOfMultipleMiddleNames() {
      assertThat(normalizer.normalize("Julia Scarlett Elizabeth Louis-Dreyfus"))
         .isEqualTo("Louis-Dreyfus, Julia S. E.");
   }

   @Disabled
   @Test
   void appendsSuffixesToEnd() {
      assertThat(normalizer.normalize("Martin Luther King, Jr."))
         .isEqualTo("King, Martin L., Jr.");
   }

   @Disabled
   @Test
   void throwsWhenNameContainsTwoCommas() {
      var thrown = assertThrows(InvalidNameException.class, () ->
         normalizer.normalize("Thurston, Howell, III"));
      assertThat(thrown.getMessage())
         .isEqualTo("name contains 2 commas; maximum is one comma");
   }
}
