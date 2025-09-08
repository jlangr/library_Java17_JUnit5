package com.langrsoft.persistence;

import com.langrsoft.service.library.DuplicateHoldingException;
import com.langrsoft.domain.Branch;
import com.langrsoft.domain.Holding;
import com.langrsoft.domain.HoldingBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static testutil.CollectionsUtil.soleElement;

class HoldingStoreTest {
    HoldingStore store;
    Holding savedHolding;

    @BeforeEach
    void setUp() {
        HoldingStore.deleteAll();
        store = new HoldingStore();
    }

    @Nested
    class Errors {
        @BeforeEach
        void saveHolding() {
            savedHolding = new HoldingBuilder().classification("QA123").build();
            store.save(savedHolding);
        }

        @Test
        void throwsWhenSavingHoldingWithDuplicateBarcode() {
            var duplicateHolding = new Holding(savedHolding.getMaterial(), savedHolding.getBranch(), savedHolding.getCopyNumber());
            assertThrows(DuplicateHoldingException.class, () -> store.save(duplicateHolding));
        }
    }

    @Nested
    class FindByClassification {
        @Nested
        class WithSavedHolding {
            private List<Holding> retrieved;

            @BeforeEach
            void findSavedHoldingByClassification() {
                savedHolding = new HoldingBuilder().build();
                store.save(savedHolding);
                retrieved = store.findByClassification(classification(savedHolding));
            }

            @Test
            void returnsListContainingSavedHoldings() {
                assertThat(soleElement(retrieved).getMaterial()).isEqualTo(savedHolding.getMaterial());
            }

            @Test
            void returnsNewInstance() {
                assertThat(soleElement(retrieved)).isNotSameAs(savedHolding);
            }
        }

        @Test
        void returnsEmptyListWhenNothingFound() {
            assertThat(store.findByClassification("nonexistent")).isEmpty();
        }

        private String classification(Holding holding) {
            return holding.getMaterial().getClassification();
        }
    }

    @Nested
    class FindByBarCode {
        @BeforeEach
        void saveHolding() {
            savedHolding = new HoldingBuilder().build();
            store.save(savedHolding);
        }

        @Test
        void returnsMatchingHolding() {
            var holding = store.findByBarcode(savedHolding.getBarcode());

            assertThat(holding.getBarcode()).isEqualTo(savedHolding.getBarcode());
        }

        @Test
        void returnsNullWhenNotFound() {
            assertThat(store.findByBarcode("NONEXISTENT:1")).isNull();
        }
    }

    @Nested
    class FindByBranch {
        @BeforeEach
        void saveHolding() {
            savedHolding = new HoldingBuilder().build();
            store.save(savedHolding);
        }

        @Test
        void returnsEmptyListWhenNotFound() {
            assertThat(store.findByBranch("nonexistent scancode")).isEmpty();
        }

        @Test
        void returnsHoldingsWithMatchingBranchScanCode() {
            var branch = new Branch("scancode", "");
            var holding = new HoldingBuilder().branch(branch).copyNumber(2).build();
            store.save(holding);

            var holdings = store.findByBranch("scancode");

            assertThat(holdings).containsExactly(holding);
        }
    }
}
