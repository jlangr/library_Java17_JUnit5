package com.langrsoft.service.library;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.langrsoft.util.ListUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BranchServiceTest {
    private BranchService service;

    @BeforeEach
    void initialize() {
        service = new BranchService();
        new LibraryData().deleteBranchesHoldingsPatrons();
    }

    @Test
    void findsByScanCode() {
        service.add("name", "b2");

        var branch = service.find("b2");

        assertThat(branch.getName()).isEqualTo("name");
    }

    @Test
    void rejectsDuplicateScanCode() {
        service.add("", "b559");
        assertThrows(DuplicateBranchCodeException.class, () ->
           service.add("", "b559"));
    }

    @Test
    void rejectsScanCodeNotStartingWithB() {
        assertThrows(InvalidBranchCodeException.class, () ->
           service.add("", "c2234"));
    }

    @Test
    void answersGeneratedId() {
        var scanCode = service.add("");

        assertThat(scanCode).startsWith("b");
    }

    @Test
    void findsBranchMatchingScanCode() {
        var scanCode = service.add("a branch");

        var branch = service.find(scanCode);

        assertThat(branch.getName()).isEqualTo("a branch");
        assertThat(branch.getScanCode()).isEqualTo(scanCode);
    }

    @Test
    void returnsListOfAllPersistedBranches() {
        var eastScanCode = service.add("e");
        var westScanCode = service.add("w");

        var all = service.allBranches();

        var scanCodes = new ListUtil().map(all, "getScanCode", Branch.class, String.class);
        assertThat(scanCodes).containsExactly(eastScanCode, westScanCode);
    }
}
