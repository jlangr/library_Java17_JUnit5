package com.langrsoft.domain;

import com.langrsoft.external.Material;
import com.langrsoft.persistence.HoldingStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class CatalogTest {
    Catalog catalog = new Catalog();
    HoldingBuilder holdingBuilder = new HoldingBuilder();

    @BeforeEach
    void initialize() {
        HoldingStore.deleteAll();
    }

    @Test
    void isEmptyOnCreation() {
        assertThat(catalog.size()).isEqualTo(0);
    }

    @Test
    void incrementsSizeWhenMaterialAdded() {
        catalog.add(holdingBuilder.build());

        assertThat(catalog.size()).isEqualTo(1);
    }

    @Test
    void answersEmptyForNonexistentMaterial() {
        assertThat(catalog.findAll("nonexistentid")).isEmpty();
    }

    @Test
    void findAllReturnsListOfHoldings() {
        var classification = "123";
        var barcode = addHoldingWithClassification(classification);
        var barcode2 = addHoldingWithClassification(classification);

        var holdings = catalog.findAll(classification);

        var retrieved1 = catalog.find(barcode);
        var retrieved2 = catalog.find(barcode2);
        assertThat(holdings).containsExactly(retrieved1, retrieved2);
    }

    private String addHoldingWithClassification(String classification) {
        var material = new Material("", "", "", classification, "");
        var holding = holdingBuilder.material(material).build();
        return catalog.add(holding);
    }

    @Test
    void findAllReturnsOnlyHoldingsWithMatchingClassification() {
        var barcode1 = addHoldingWithClassification("123");
        addHoldingWithClassification("456");

        var retrieved = catalog.findAll("123");

        assertThat(retrieved).hasSize(1);
        assertThat(retrieved.get(0).getBarcode()).isEqualTo(barcode1);
    }

    @Test
    void retrievesHoldingUsingBarcode() {
        var holding = holdingBuilder.build();
        var barcode = catalog.add(holding);

        var retrieved = catalog.find(barcode);

        assertThat(retrieved).isEqualTo(holding);
    }

    @Test
    void incrementsCopyNumberWhenSameClassificationExists() {
        var holding = holdingBuilder.build();
        catalog.add(holding);
        var barcode = catalog.add(holding);

        var retrieved = catalog.find(barcode);

        assertThat(retrieved.getCopyNumber()).isEqualTo(2);
    }

    @Test
    void supportsIteration() {
        var barcode1 = addHoldingWithClassification("1");
        var barcode2 = addHoldingWithClassification("2");

        var results = new ArrayList<>();
        for (var holding : catalog)
            results.add(holding.getBarcode());

        assertThat(results).containsExactlyInAnyOrder(barcode1, barcode2);
    }
}
