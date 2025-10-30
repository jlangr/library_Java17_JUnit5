package com.langrsoft.service.library;

import com.langrsoft.domain.ClassificationApiFactory;
import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingService_CheckInCheckOutTest {
    HoldingService service = new HoldingService();
    ClassificationApi classificationApi = mock(ClassificationApi.class);
    BranchService branchService = new BranchService();
    PatronService patronService = new PatronService();
    String branchScanCode;
    String patronId;

    @BeforeEach
    void setUp() {
        new LibraryData().deleteBranchesHoldingsPatrons();
        ClassificationApiFactory.setService(classificationApi);
        branchScanCode = branchService.add("main");
        patronId = patronService.add("Joe");
    }

    private String addHolding(String sourceId) {
        var material = new Material(sourceId, "", "", "", "");
        when(classificationApi.retrieveMaterial(sourceId)).thenReturn(material);
        return service.add(sourceId, branchScanCode);
    }

    @Test
    void checkOutMarksHoldingUnavailable() {
        var barcode = addHolding("123");
        service.checkOut(patronId, barcode, new Date());

        assertThat(service.isAvailable(barcode)).isFalse();
    }

    @Test
    void checkOutThrowsWhenHoldingNotFound() {
        assertThrows(com.langrsoft.domain.HoldingNotFoundException.class, () ->
           service.checkOut(patronId, "999:1", new Date()));
    }

    @Test
    void checkInMarksHoldingAvailable() {
        var barcode = addHolding("123");
        service.checkOut(patronId, barcode, new Date());
        service.checkIn(barcode, new Date(), branchScanCode);

        assertThat(service.isAvailable(barcode)).isTrue();
    }
}
