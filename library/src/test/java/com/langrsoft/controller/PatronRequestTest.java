package com.langrsoft.controller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class PatronRequestTest {
    @Test
    public void includesIdAndNameInToString() {
        var patronRequest = new PatronRequest();
        patronRequest.name = "myName";
        patronRequest.id = "42";
        assertThat(patronRequest.toString(), containsString("myName"));
        assertThat(patronRequest.toString(), containsString("42"));
    }
}
