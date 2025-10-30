package com.langrsoft.external;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("slow")
abstract class ClassificationApiContract {
    private Material material;

    @BeforeEach
    void createAndRetrieve() {
        ClassificationApi classificationApiImplementation = createClassificationApiImpl();
        material = classificationApiImplementation.retrieveMaterial(validQueryIsbn());
    }

    @Test
    void populatesCriticalFields() {
        assertThat(material.getAuthor()).isNotNull();
        assertThat(material.getTitle()).isNotNull();
        assertThat(material.getClassification()).isNotNull();
    }

    @Test
    void echosSourceId() {
        assertThat(material.getSourceId()).isEqualTo(validQueryIsbn());
    }

    @Test
    void populatesFormatWithEnumValue() {
        assertThat(material.getFormat()).isIn((Object[]) MaterialType.values());
    }

    @Test
    void populatesYearWithReasonableValue() {
        var currentYear = LocalDate.now().getYear();
        assertThat(Integer.parseInt(material.getYear()))
           .isGreaterThan(1440)
           .isLessThanOrEqualTo(currentYear);
    }


    abstract protected ClassificationApi createClassificationApiImpl();

    abstract protected String validQueryIsbn();
}
