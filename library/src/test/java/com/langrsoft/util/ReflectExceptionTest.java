package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectExceptionTest {
    @Test
    public void storesCause() {
        var cause = new IllegalArgumentException("alpha");

        var reflectException = new ReflectException(cause);

        assertThat(reflectException.getCause().getMessage()).isEqualTo("alpha");
    }
}