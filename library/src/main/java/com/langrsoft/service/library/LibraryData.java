package com.langrsoft.service.library;

import com.langrsoft.persistence.BranchStore;
import com.langrsoft.persistence.HoldingStore;
import com.langrsoft.persistence.PatronStore;

public class LibraryData {
    public LibraryData() {}

    public void deleteBranchesHoldingsPatrons() {
        BranchStore.deleteAll();
        HoldingStore.deleteAll();
        PatronStore.deleteAll();
    }
}
