package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class ReflectExceptionTest {
    @Test
    public void storesCause() {
        var cause = new IllegalArgumentException("alpha");

        var reflectException = new ReflectException(cause);

        assertThat(reflectException.getCause().getMessage(), equalTo("alpha"));
    }
}