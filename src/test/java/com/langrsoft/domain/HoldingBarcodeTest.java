package com.langrsoft.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HoldingBarcodeTest {
    @Test
    void splitsBarcodeProvidedIntoCopyAndClassification() {
        var barcode = new HoldingBarcode("ABC:42");

        assertThat(barcode.getClassification()).isEqualTo("ABC");
        assertThat(barcode.getCopyNumber()).isEqualTo(42);
    }

    @Test
    void barcode_causesThrowWhenNoColonExists() {
        assertThrows(IllegalArgumentException.class, () -> new HoldingBarcode("abc"));
    }

    @Test
    void barcode_causesThrowWhenCopyNumberNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> new HoldingBarcode("abc:-124"));
    }

    @Test
    void barcode_causesThrowWhenCopyNumberNotPositiveInt() {
        assertThrows(IllegalArgumentException.class, () -> new HoldingBarcode("abc:xyz"));
    }

    @Nested
    class EqualsAndHashcode {
        HoldingBarcode barcode = new HoldingBarcode("A", 1);
        HoldingBarcode barcode1copy = new HoldingBarcode("A", 1);
        HoldingBarcode barcode2 = new HoldingBarcode("B", 1);

        @Test
        void usesHashCode() {
            assertThat(barcode.hashCode()).isEqualTo(barcode1copy.hashCode());
            assertThat(barcode.hashCode()).isNotEqualTo(barcode2.hashCode());
        }

        @Test
        void equality() {
            assertThat(barcode.equals(barcode1copy)).isTrue();
            assertThat(barcode.equals(barcode2)).isFalse();
        }

        @Test
        void returnsFalseWhenComparingToNull() {
            assertThat(barcode.equals(null)).isFalse();
        }

        @Test
        void returnsFalseWhenComparingToWrongType() {
            assertThat(barcode.equals("")).isFalse();
        }
    }

    @Test
    void canBeConstructedFromClassificatonAndCopy() {
        var barcode = new HoldingBarcode("ABC", 42);

        assertThat(barcode.getClassification()).isEqualTo("ABC");
        assertThat(barcode.getCopyNumber()).isEqualTo(42);
    }

    @Test
    void canDeriveCopyNumberFromBarcode() {
        assertThat(HoldingBarcode.getCopyNumber("QA123:2")).isEqualTo(2);
    }

    @Test
    void canDeriveClassificationFromBarcode() {
        assertThat(HoldingBarcode.getClassification("QA123:2")).isEqualTo("QA123");
    }

    @Test
    void usesBarcodeForToString() {
        var barcode = HoldingBarcode.createCode("QA123", 20);
        assertThat(barcode).isEqualTo("QA123:20");
        assertThat(new HoldingBarcode("QA123:20").toString()).isEqualTo(barcode);
    }
}
