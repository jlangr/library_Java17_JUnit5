package com.langrsoft.persistence;

import com.langrsoft.domain.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BranchStoreTest {
    private BranchStore store;
    private static final Branch EAST_BRANCH = new Branch("East");

    @BeforeEach
    void initialize() {
        BranchStore.deleteAll();
        store = new BranchStore();
    }

    @Test
    void assignsIdToBranch() {
        var branch = new Branch("name");

        store.save(branch);

        assertThat(branch.getScanCode()).startsWith("b");
    }

    @Test
    void assignedIdIsUnique() {
        var branchA = new Branch("a");
        store.save(branchA);
        var branchB = new Branch("b");

        store.save(branchB);

        assertThat(branchA.getScanCode()).isNotEqualTo(branchB.getScanCode());
    }

    @Test
    void doesNotChangeIdIfAlreadyAssigned() {
        var branch = new Branch("b1964", "");

        store.save(branch);

        assertThat(branch.getScanCode()).isEqualTo("b1964");
    }

    @Test
    void returnsSavedBranches() {
        store.save(new Branch("name"));

        var retrieved = store.findByName("name");

        assertThat(retrieved.getName()).isEqualTo("name");
    }

    @Test
    void returnsNewInstanceOfPersistedBranch() {
        var branch = new Branch("name");
        store.save(branch);
        store = new BranchStore();

        var retrieved = store.findByName("name");

        assertThat(branch).isNotSameAs(retrieved);
    }

    @Test
    void returnsListOfAllBranches() {
        var branch = new Branch("b123", "");
        store.save(branch);

        var branches = store.getAll();

        assertThat(branches).containsExactly(branch);
    }

    @Test
    void findsBranchByScanCode() {
        store.save(EAST_BRANCH);

        var retrieved = store.findByScanCode(EAST_BRANCH.getScanCode());

        assertThat(retrieved).isEqualTo(EAST_BRANCH);
    }

    @Test
    void findsCheckedOutBranch() {
        assertThat(store.findByScanCode(Branch.CHECKED_OUT.getScanCode()))
           .isSameAs(Branch.CHECKED_OUT);
    }

    @Test
    void findsBranchByScanCodeReturnsNullWhenNotFound() {
        assertThat(store.findByScanCode("")).isNull();
    }
}
