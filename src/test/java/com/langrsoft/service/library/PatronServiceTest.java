package com.langrsoft.service.library;

import com.langrsoft.persistence.PatronStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatronServiceTest {
    private final String invalidCardNumberFormat = "INVALID_FORMAT";
    private final String invalidCardNumber = "99999999";
    private final String cardNumber = "card number";
    private final String name = "name";
    private final String pid = "pid";
    PatronService service;
    private CheckCreditStub checkCreditMock;

    @BeforeEach
    void initialize() {
        PatronStore.deleteAll();
        service = new PatronService();

        checkCreditMock = mock(CheckCreditStub.class);
        when(checkCreditMock.hasCreditValid(cardNumber)).thenReturn(true);
        when(checkCreditMock.hasCreditValid(invalidCardNumber)).thenReturn(false);

        service.addValidator(checkCreditMock);
    }

    @Test
    void testAddValid() {
        assertThat(service.add(pid, name, cardNumber)).isNotNull();
    }

    @Test
    void testAddFailed() {
        assertThat(service.add(pid, name, invalidCardNumber)).isNull();
    }

    @Test
    void testAddFailedOnInvalidCardNumberFormat() {
        when(checkCreditMock.hasCreditValid(invalidCardNumberFormat)).thenThrow(InvalidCardNumber.class);

        Assertions.assertThrows(InvalidCardNumber.class,
                () -> service.add(pid, name, invalidCardNumberFormat));
    }

    @Test
    void answersGeneratedId() {
        var scanCode = service.add(name);

        assertThat(scanCode).startsWith("p");
    }

    @Test
    void allowsAddingPatronWithId() {
        service.add("p123", "xyz");

        var patron = service.find("p123");

        assertThat(patron.getName()).isEqualTo("xyz");
    }

    @Test
    void rejectsPatronIdNotStartingWithP() {
        assertThrows(InvalidPatronIdException.class, () ->
                service.add("234", ""));
    }

    @Test
    void rejectsAddOfDuplicatePatron() {
        service.add("p556", "");
        assertThrows(DuplicatePatronException.class, () ->
                service.add("p556", ""));
    }

    @Test
    void answersNullWhenPatronNotFound() {
        assertThat(service.find("nonexistent id")).isNull();
    }
}
