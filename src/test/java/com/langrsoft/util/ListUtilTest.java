package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class ListUtilTest {
    @Test
    void mapTransformsUsingGetter() {
        var s = asList("1", "22", "333");

        var mappedToLengths = new ListUtil().map(s, "length", String.class, Integer.class);

        assertThat(mappedToLengths).containsExactly(1, 2, 3);
    }
}