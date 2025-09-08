package com.langrsoft.service.scanner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BarcodeInterpreterTest {
    @Test
    void returnsHoldingTypeWhenBarcodeContainsColon() {
        assertThat(BarcodeInterpreter.typeOf("123:1")).isEqualTo(BarcodeType.HOLDING);
    }

    @Test
    void returnsBranchTypeWhenStartsWithB() {
        assertThat(BarcodeInterpreter.typeOf("b123")).isEqualTo(BarcodeType.BRANCH);
        assertThat(BarcodeInterpreter.typeOf("B123")).isEqualTo(BarcodeType.BRANCH);
    }

    @Test
    void returnsInventoryTypeWhenStartsWithI() {
        assertThat(BarcodeInterpreter.typeOf("i111")).isEqualTo(BarcodeType.INVENTORY);
        assertThat(BarcodeInterpreter.typeOf("I111")).isEqualTo(BarcodeType.INVENTORY);
    }

    @Test
    void returnsPatronTypeWhenStartsWithP() {
        assertThat(BarcodeInterpreter.typeOf("p123")).isEqualTo(BarcodeType.PATRON);
    }

    @Test
    void returnsUnrecognizedTypeWhenOther() {
        assertThat(BarcodeInterpreter.typeOf("123")).isEqualTo(BarcodeType.UNRECOGNIZED);
    }
}