package com.langrsoft.service.library;

import com.langrsoft.domain.ClassificationApiFactory;
import com.langrsoft.external.ClassificationApi;
import com.langrsoft.external.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HoldingService_FindByBranchTest {
    HoldingService service = new HoldingService();
    ClassificationApi classificationApi = mock(ClassificationApi.class);
    BranchService branchService = new BranchService();

    @BeforeEach
    void initialize() {
        new LibraryData().deleteBranchesHoldingsPatrons();
        ClassificationApiFactory.setService(classificationApi);
    }

    private void addHolding(String sourceId, String branchScanCode) {
        when(classificationApi.retrieveMaterial(sourceId))
           .thenReturn(new Material(sourceId, "", "", "", ""));
        service.add(sourceId, branchScanCode);
    }

    @Test
    void returnsOnlyHoldingsAtBranch() {
        var branchAScanCode = branchService.add("branch A");
        var branchBScanCode = new BranchService().add("branch B");
        addHolding("123", branchAScanCode);
        addHolding("456", branchAScanCode);
        addHolding("999", branchBScanCode);

        var holdings = service.findByBranch(branchAScanCode);

        var holdingSourceIds = holdings.stream()
           .map(h -> h.getMaterial().getSourceId())
           .toList();
        assertThat(holdingSourceIds).containsExactly("123", "456");
    }
}