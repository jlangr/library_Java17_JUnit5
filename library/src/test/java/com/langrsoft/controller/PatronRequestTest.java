package com.langrsoft.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatronRequestTest {
    @Test
    public void includesIdAndNameInToString() {
        var patronRequest = new PatronRequest();
        patronRequest.name = "myName";
        patronRequest.id = "42";

        assertThat(patronRequest.toString()).contains("myName");
        assertThat(patronRequest.toString()).contains("42");
    }
}