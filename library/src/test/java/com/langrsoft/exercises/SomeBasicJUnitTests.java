package com.langrsoft.exercises;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class SomeBasicJUnitTests {
    @Disabled("uncomment when ready")
    @Test
    void supportsBasicMath() {
        assertThat(4 * 8).isEqualTo(32);
    }

    @Disabled("uncomment when ready")
    @Test
    void appendsItemToListUsingAdd() {
        var numbers = new ArrayList<>(asList(12, 1, 1, 1, 2, 1, 3));

        numbers.add(1);

        assertThat(numbers).isEqualTo(asList(12, 1, 1, 1, 2, 1, 3, 1));
    }

    @Disabled("uncomment when ready")
    @Test
    void doublesEachElementInAList() {
        List<Integer> numbers = List.of(2, 5, 10, 105);

        var result = numbers.stream()
           .map(n -> n * 2)
           .toList();

        assertThat(result).isEqualTo(asList(4, 10, 20, 210));
    }

    @Disabled("uncomment when ready")
    @Test
    void handlesInterestingFloatPointResults() {
        var result = 0.1 + 0.2;

        assertThat(result).isCloseTo(0.3, within(1e-10));
    }
}
