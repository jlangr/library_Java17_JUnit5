package com.langrsoft.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultiMapTest {
    private MultiMap<Object, Object> map;

    @BeforeEach
    void initialize() {
        map = new MultiMap<>();
    }

    @Test
    void isEmptyOnCreation() {
        assertThat(map.size()).isEqualTo(0);
        assertThat(map.valuesSize()).isEqualTo(0);
        assertThat(map.isEmpty()).isTrue();
    }

    @Test
    void returnsValuesAssociatedWithKeyAsList() {
        map.put("a", "alpha");

        var values = map.get("a");

        assertThat(values).containsExactlyInAnyOrder("alpha");
    }

    @Test
    void incrementsSizeForMultipleKeys() {
        map.put("a", "");
        map.put("b", "");

        assertThat(map.size()).isEqualTo(2);
    }

    @Test
    void allowsMultipleElementsSameKey() {
        map.put("a", "alpha1");
        map.put("a", "alpha2");

        var values = map.get("a");

        assertThat(values).containsExactlyInAnyOrder("alpha1", "alpha2");
    }

    @Test
    void valuesSizeRepresentsTotalCountOfValues() {
        map.put("a", "alpha");
        map.put("b", "beta1");
        map.put("b", "beta2");

        assertThat(map.valuesSize()).isEqualTo(3);
    }

    @Test
    void returnsOnlyValuesAssociatedWithKey() {
        map.put("a", "alpha");
        map.put("b", "beta");

        var values = map.get("b");

        assertThat(values).containsExactlyInAnyOrder("beta");
    }

    @Test
    void throwsOnPutOfNullKey() {
        assertThrows(NullPointerException.class, () ->
           map.put(null, ""));
    }

    @Test
    void clearEliminatesAllData() {
        map.put("a", "");
        map.put("b", "");

        map.clear();

        assertThat(map.size()).isEqualTo(0);
        assertThat(map.valuesSize()).isEqualTo(0);
    }

    @Test
    void returnsCombinedCollectionOfAllValues() {
        map.put("a", "alpha");
        map.put("b", "beta");

        var values = map.values();

        assertThat(values).containsExactlyInAnyOrder("alpha", "beta");
    }
}
