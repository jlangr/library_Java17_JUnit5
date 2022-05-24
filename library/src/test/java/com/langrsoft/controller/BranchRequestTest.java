package com.langrsoft.controller;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

class BranchRequestTest {
    Branch branch1 = new Branch("b1", "branch1");
    BranchRequest request1 = new BranchRequest(branch1);

    Branch branch2 = new Branch("b2", "branch2");
    BranchRequest request2 = new BranchRequest(branch2);

    @Test
    void createFromListOfBranches() {
        var branches = List.of(branch1, branch2);

        var branchRequests = BranchRequest.create(branches);

        assertThat(branchRequests, contains(new BranchRequest(branch1), new BranchRequest(branch2)));
    }

    @Test
    void hashCodes() {
        assertThat(request1.hashCode(), not(equalTo(request2.hashCode())));
        assertThat(request1.hashCode(), equalTo(
                new BranchRequest(new Branch("b1", "branch1")).hashCode()));
    }

    @Test
    void toStringContainsScanCodeAndName() {
        assertThat(request1.toString(), containsString("b1"));
        assertThat(request1.toString(), containsString("branch1"));
    }
}
