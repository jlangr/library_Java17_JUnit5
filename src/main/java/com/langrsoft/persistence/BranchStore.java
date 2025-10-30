package com.langrsoft.persistence;

import com.langrsoft.domain.Branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchStore {
    private static final Map<String, Branch> branches = new HashMap<>();
    private static int idIndex = 0;

    public void save(Branch branch) {
        if (branch.getScanCode().equals(""))
            branch.setScanCode("b" + BranchStore.nextId());
        branches.put(branch.getName(), copy(branch));
    }

    private static int nextId() {
        return ++idIndex;
    }

    public Branch findByName(String name) {
        return branches.get(name);
    }

    public Branch findByScanCode(String scanCode) {
        if (scanCode.equals(Branch.CHECKED_OUT.getScanCode()))
            return Branch.CHECKED_OUT;

        for (Branch branch : branches.values())
            if (branch.getScanCode().equals(scanCode))
                return branch;
        return null;
    }

    private Branch copy(Branch branch) {
        Branch newBranch = new Branch(branch.getName());
        newBranch.setScanCode(branch.getScanCode());
        return newBranch;
    }

    public List<Branch> getAll() {
        return new ArrayList<>(branches.values());
    }

    public static void deleteAll() {
        branches.clear();
    }
}
