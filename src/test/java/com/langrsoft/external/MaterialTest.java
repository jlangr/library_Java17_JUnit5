package com.langrsoft.external;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaterialTest {
    @Test
    void rendersToString() {
        var material = new Material("src", "author", "title", "QA123", "2022");

        assertThat(material.toString()).isEqualTo("BOOK: QA123 src title (author)");
    }
}
