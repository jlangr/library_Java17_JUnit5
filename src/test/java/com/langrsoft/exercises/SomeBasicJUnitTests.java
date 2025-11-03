package com.langrsoft.exercises;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class SomeBasicJUnitTests {
    @Test
    void supportsBasicMath() {
// uncomment and fix when ready
//        assertThat(4 * 8).isEqualTo(what?);
    }

    @Test
    void appendsItemToListUsingAdd() {
        var numbers = new ArrayList<>(asList(12, 1, 1, 1, 2, 1, 3));

        numbers.add(1);

// uncomment and fix when ready
//        assertThat(numbers).containsExactly(12, 1, ..what?);
    }

    @Disabled("remove this line and fix the test when ready")
    @Test
    void doublesEachElementInAList() {
        List<Integer> numbers = List.of(2, 5, 10, 105);

        // fill out the lambda with appropriate code
        List<Integer> result = numbers.stream()
//           .map(n -> /* what */)
           .toList();

        assertThat(result).isEqualTo(asList(4, 10, 20, 210));
    }

//    @Disabled("uncomment and fix when ready")
    @Test
    void handlesInterestingFloatPointResults() {
        var result = 0.1 + 0.2;

        // Unncomment this assertion and demonstrate that it fails. Why?
        // assertThat(result).isEqualTo(0.3);

        // use isCloseTo instead. See:
        // https://www.javadoc.io/static/org.assertj/assertj-core/3.24.2/org/assertj/core/api/AbstractDoubleAssert.html#isCloseTo(double,org.assertj.core.data.Offset)
        // assertThat(result).isCloseTo(/* ... fill out the code needed here ... */);
    }
}
