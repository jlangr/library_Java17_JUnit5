package com.langrsoft.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import static org.assertj.core.api.Assertions.assertThat;

class PatronTest {
    private Patron jane;

    @BeforeEach
    void initialize() {
        jane = new Patron("Jane");
    }

    @Test
    void defaultsIdToEmpty() {
        assertThat(jane.getId()).isEqualTo("");
    }

    @Test
    void fineBalanceIsZeroOnCreation() {
        assertThat(jane.fineBalance()).isEqualTo(0);
    }

    @Test
    void holdingsAreEmptyOnCreation() {
        assertThat(jane.holdingMap().isEmpty()).isTrue();
    }

    @Test
    void returnsHoldingsAdded() {
        var holding = new HoldingBuilder().build();

        jane.add(holding);

        assertThat(jane.holdingMap().holdings()).containsExactly(holding);
    }

    @Test
    void removesHoldingFromPatron() {
        var holding = new HoldingBuilder().build();
        jane.add(holding);

        jane.remove(holding);

        assertThat(jane.holdingMap().isEmpty()).isTrue();
    }

    @Test
    void storesFines() {
        jane.addFine(10);

        assertThat(jane.fineBalance()).isEqualTo(10);
    }

    @Test
    void increasesBalanceOnAdditionalFines() {
        jane.addFine(10);

        jane.addFine(30);

        assertThat(jane.fineBalance()).isEqualTo(40);
    }

    @Test
    void decreasesBalanceWhenPatronRemitsAmount() {
        jane.addFine(40);

        jane.remit(25);

        assertThat(jane.fineBalance()).isEqualTo(15);
    }

    @Test
    void supportsEqualityComparison() {
        var patron1 = new Patron("p1", "Joe");
        var patron1Copy1 = new Patron("p1", "");
        var patron1Copy2 = new Patron("p1", "");
        var patron1Subtype = new Patron("p1", "") {
        };
        var patron2 = new Patron("p2", "");

        new EqualityTester(patron1, patron1Copy1, patron1Copy2, patron2, patron1Subtype).verify();
    }

    @Test
    void hasHoldingReturnsFalseWhenNoHoldingsAdded() {
       Patron patron = new Patron("p1", "Joe");

        assertThat(patron.hasHolding(new HoldingBuilder().build())).isFalse();
    }

    @Test
    void hasHoldingReturnsTrueWhenHoldingAddedAndMatches() {
        Patron patron = new Patron("p1", "Joe");
        Holding holding = new HoldingBuilder().classification("h1").build();

        patron.add(holding);

        assertThat(patron.hasHolding(holding)).isTrue();
    }

    @Test
    void hasHoldingReturnsFalseWhenHoldingAddedAndDoesNotMatch() {
        Patron patron = new Patron("p1", "Joe");
        Holding holdingA = new HoldingBuilder().classification("A1").build();
        Holding holdingB = new HoldingBuilder().classification("B1").build();

        patron.add(holdingA);

        assertThat(patron.hasHolding(holdingB)).isFalse();
    }

}
