package com.langrsoft.controller;

import com.langrsoft.domain.HoldingBuilder;
import com.langrsoft.external.Material;
import com.langrsoft.external.MaterialType;
import com.langrsoft.util.DateUtil;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class HoldingResponseTest {
    @Test
    void createFromHolding() {
        var material = new Material(
           "source", "author", "title", "classif",
           MaterialType.BOOK, "1999");
        var checkoutDate = DateUtil.create(2023, 2, 1);
        var holding = new HoldingBuilder()
           .copyNumber(42)
           .material(material)
           .classification("QA123")
           .checkout(checkoutDate)
           .build();

        var holdingResponse = new HoldingResponse(holding);

        assertThat(holdingResponse.getBarcode()).isEqualTo("QA123:42");
        assertThat(holdingResponse.getCopyNumber()).isEqualTo(42);
        assertThat(holdingResponse.getDateCheckedOut()).isEqualTo(checkoutDate);
        assertThat(holdingResponse.getDateDue()).isEqualTo(holding.dateDue());
        assertThat(holdingResponse.getIsAvailable()).isFalse();
        assertThat(holdingResponse.getDateLastCheckedIn()).isNull();
        assertThat(holdingResponse.getAuthor()).isEqualTo("author");
        assertThat(holdingResponse.getTitle()).isEqualTo("title");
        assertThat(holdingResponse.getYear()).isEqualTo("1999");
        assertThat(holdingResponse.getFormat()).isEqualTo("BOOK");
    }

    @Test
    void createFromHoldings() {
        var holding1 = new HoldingBuilder().build();
        var holding2 = new HoldingBuilder().build();

        var holdingResponses = HoldingResponse.create(asList(holding1, holding2));

        assertThat(holdingResponses)
           .satisfiesExactlyInAnyOrder(
              r -> assertThat(r).usingRecursiveComparison().isEqualTo(new HoldingResponse(holding1)),
              r -> assertThat(r).usingRecursiveComparison().isEqualTo(new HoldingResponse(holding2))
           );
    }
}