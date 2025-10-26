package com.langrsoft.service.library;

import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.Material;
import com.langrsoft.domain.BranchNotFoundException;
import com.langrsoft.domain.ClassificationApiFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingServiceTest {
    HoldingService service = new HoldingService();
    ClassificationApi classificationApi = mock(ClassificationApi.class);
    String branchScanCode;

    @BeforeEach
    void initialize() {
        new LibraryData().deleteBranchesHoldingsPatrons();
        ClassificationApiFactory.setService(classificationApi);
        branchScanCode = new BranchService().add("");
    }

    @Test
    void usesClassificationServiceToRetrieveBookDetails() {
        var isbn = "9780141439594";
        var material = new Material(isbn, "", "", "", "");
        when(classificationApi.retrieveMaterial(isbn)).thenReturn(material);
        var barcode = service.add(isbn, branchScanCode);

        var holding = service.findHolding(barcode);

        assertThat(holding.getMaterial().getSourceId()).isEqualTo(isbn);
    }

    @Test
    void addThrowsWhenSourceIdNotFound() {
        assertThrows(InvalidSourceIdException.class, () ->
           service.add("nonexistent", branchScanCode));
    }

    @Test
    void throwsExceptionWhenBranchNotFound() {
        var thrown = assertThrows(BranchNotFoundException.class, () ->
           service.add("", "badBranchId"));
        assertThat(thrown.getMessage()).isEqualTo("Branch not found: badBranchId");
    }
}
