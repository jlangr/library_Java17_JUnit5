package com.langrsoft.controller;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class MaterialRequestTest {
    @Test
    void toStringReturnsPrintableRepresentation() {
        var materialRequest = new MaterialRequest();
        materialRequest.setSourceId("aSourceId");
        materialRequest.setTitle("aTitle");
        materialRequest.setAuthor("anAuthor");
        materialRequest.setYear("aYear");
        materialRequest.setClassification("aClassification");
        materialRequest.setFormat("BOOK");

        var s = materialRequest.toString();

        assertThat(s, containsString("aSourceId"));
        assertThat(s, containsString("aTitle"));
        assertThat(s, containsString("anAuthor"));
        assertThat(s, containsString("aYear"));
        assertThat(s, containsString("aClassification"));
        assertThat(s, containsString("BOOK"));
    }
}
