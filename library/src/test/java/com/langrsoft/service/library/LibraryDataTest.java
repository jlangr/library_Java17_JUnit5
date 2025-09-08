package com.langrsoft.service.library;

import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.Material;
import com.langrsoft.domain.Branch;
import com.langrsoft.domain.ClassificationApiFactory;
import com.langrsoft.domain.Patron;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LibraryDataTest {
    PatronService patronService = new PatronService();
    HoldingService holdingService = new HoldingService();
    BranchService branchService = new BranchService();
    ClassificationApi classificationApi;

    @BeforeEach
    void setUpClassificationService() {
        classificationApi = mock(ClassificationApi.class);
        ClassificationApiFactory.setService(classificationApi);
    }

    @Test
    void deleteAllRemovesAllPatrons() {
        patronService.patronAccess.add(new Patron("", "1"));
        branchService.add("2");
        var material = new Material("3", "", "", "", "");
        when(classificationApi.retrieveMaterial("3")).thenReturn(material);
        holdingService.add(material.getSourceId(), Branch.CHECKED_OUT.getScanCode());

        new LibraryData().deleteBranchesHoldingsPatrons();

        assertThat(patronService.allPatrons()).isEmpty();
        assertThat(holdingService.allHoldings()).isEmpty();
        assertThat(branchService.allBranches()).isEmpty();
    }
}
