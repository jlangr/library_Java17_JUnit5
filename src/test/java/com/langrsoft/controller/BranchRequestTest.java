package com.langrsoft.controller;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BranchRequestTest {
    Branch branch1 = new Branch("b1", "branch1");
    BranchRequest request1 = new BranchRequest(branch1);

    Branch branch2 = new Branch("b2", "branch2");
    BranchRequest request2 = new BranchRequest(branch2);

    @Test
    void createFromListOfBranches() {
        var branches = List.of(branch1, branch2);

        var branchRequests = BranchRequest.create(branches);

        assertThat(branchRequests)
           .containsExactly(new BranchRequest(branch1), new BranchRequest(branch2));
    }

    @Test
    void hashCodes() {
        assertThat(request1.hashCode())
           .isNotEqualTo(request2.hashCode());
        assertThat(request1.hashCode())
           .isEqualTo(new BranchRequest(new Branch("b1", "branch1")).hashCode());
    }

    @Test
    void toStringContainsScanCodeAndName() {
        assertThat(request1.toString())
           .contains("b1")
           .contains("branch1");
    }
}