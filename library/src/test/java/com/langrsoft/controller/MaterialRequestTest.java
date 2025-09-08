package com.langrsoft.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(s).contains("aSourceId");
        assertThat(s).contains("aTitle");
        assertThat(s).contains("anAuthor");
        assertThat(s).contains("aYear");
        assertThat(s).contains("aClassification");
        assertThat(s).contains("BOOK");
    }
}