package com.langrsoft.persistence;

import com.langrsoft.domain.HoldingBuilder;
import com.langrsoft.domain.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static testutil.CollectionsUtil.soleElement;

class PatronStoreTest {
    PatronStore store;
    static Patron patronSmith = new Patron("p1", "joe");

    @BeforeEach
    void initialize() {
        PatronStore.deleteAll();
        store = new PatronStore();
    }

    @Test
    void persistsAddedPatron() {
        store.add(patronSmith);

        var patrons = store.getAll();

        assertThat(soleElement(patrons)).isEqualTo(patronSmith);
    }

    @Test
    void assignsId() {
        var patron = new Patron("name");

        store.add(patron);

        assertThat(patron.getId()).startsWith("p");
    }

    @Test
    void assignedIdIsUnique() {
        var patronA = new Patron("a");
        var patronB = new Patron("b");

        store.add(patronA);
        store.add(patronB);

        assertThat(patronA.getId()).isNotEqualTo(patronB.getId());
    }

    @Test
    void doesNotOverwriteExistingId() {
        var patron = new Patron("p12345", "");

        store.add(patron);

        assertThat(store.find("p12345").getId()).isEqualTo("p12345");
    }

    @Test
    void returnsPersistedPatronAsNewInstance() {
        store.add(patronSmith);

        var found = store.find(patronSmith.getId());

        assertThat(found).isNotSameAs(patronSmith);
    }

    @Test
    void storesHoldingsAddedToPatron() {
        var holding = new HoldingBuilder().build();
        store.add(patronSmith);
        store.addHoldingToPatron(patronSmith, holding);

        var patron = store.find(patronSmith.getId());

        assertThat(patron.holdingMap().holdings()).containsExactly(holding);
    }

    @Test
    void throwsOnAddingHoldingToNonexistentPatron() {
        var holding = new HoldingBuilder().build();
        assertThrows(PatronNotFoundException.class, () ->
           store.addHoldingToPatron(patronSmith, holding));
    }

    @Test
    void findsPersistedPatronById() {
        store.add(patronSmith);

        var found = store.find(patronSmith.getId());

        assertThat(found.getName()).isEqualTo(patronSmith.getName());
    }
}