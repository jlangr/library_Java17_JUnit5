package com.langrsoft.service.scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;

public abstract class ScanStationStateTestBase extends MockedScannerSubsystemFields {

    @BeforeEach
    public void injectStateObject() {
        state = createStateObject();
        scanner.setCurrentState(state);
    }

    abstract protected ScanStationState createStateObject();

    protected void assertMessageDisplayed(String text) {
        verify(display).showMessage(text);
    }

    protected void assertStateUnchanged() {
        assertSame(scanner.getCurrentState(), state);
    }

    protected void assertCurrentState(Class<?> expectedState) {
        assertThat(scanner.getCurrentState()).isInstanceOf(expectedState);
    }

    @Test
    void toStringSpecifiesStateName() {
        var className = state.getClass().getSimpleName();
        assertThat(state.toString()).isEqualTo("state: " + className);
    }
}