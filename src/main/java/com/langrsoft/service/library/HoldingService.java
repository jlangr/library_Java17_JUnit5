package com.langrsoft.service.library;

import com.langrsoft.domain.*;
import com.langrsoft.external.Material;
import com.langrsoft.persistence.PatronNotFoundException;
import com.langrsoft.persistence.PatronStore;

import java.util.Date;
import java.util.List;

public class HoldingService {
    private final Catalog catalog = new Catalog();

    public String add(String sourceId, String branchId) {
        Branch branch = getBranchOrThrow(branchId);
        Holding holding = new Holding(retrieveMaterialDetails(sourceId), branch);
        catalog.add(holding);
        return holding.getBarcode();
    }

    private Material retrieveMaterialDetails(String sourceId) {
        Material material = ClassificationApiFactory.getService().retrieveMaterial(sourceId);
        if (material == null)
            throw new InvalidSourceIdException("cannot retrieve material with source ID " + sourceId);
        return material;
    }

    private Branch getBranchOrThrow(String branchId) {
        Branch branch = new BranchService().find(branchId);
        if (branch == null)
            throw new BranchNotFoundException("Branch not found: " + branchId);
        return branch;
    }

    public boolean isAvailable(String barCode) {
        Holding holding = getHoldingOrThrow(barCode);
        return holding.isAvailable();
    }

    public HoldingMap allHoldings() {
        HoldingMap stack = new HoldingMap();
        for (Holding holding : catalog)
            stack.add(holding);
        return stack;
    }

    public Holding find(String barCode) {
        return catalog.find(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return catalog.findByBranch(branchScanCode);
    }

    public void transfer(String barcode, String branchScanCode) {
        Holding holding = getHoldingOrThrow(barcode);
        Branch branch = getBranchOrThrow(branchScanCode);
        holding.transfer(branch);
    }

    public void checkOut(String patronId, String barCode, Date date) {
        Holding holding = getHoldingOrThrow(barCode);
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
        holding.checkOut(date);

        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    public void checkIn(String barCode, Date date, String branchScanCode) {
        Branch branch = getBranchOrThrow(branchScanCode);
        Holding holding = getHoldingOrThrow(barCode);
        holding.checkIn(date, branch);

        Patron patron = findPatronByHolding(holding);
        patron.remove(holding);

        if (isLateReturn(holding)) {
            int daysLate = holding.daysLate();
            int fineBasis = holding.getMaterial().getFormat().getDailyFine();
            int fine = calculateFine(holding, daysLate, fineBasis);
            patron.addFine(fine);
        }
    }

    private Holding getHoldingOrThrow(String barCode) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding;
    }

    private Patron findPatronByHolding(Holding holding) {
        for (Patron patron : new PatronService().allPatrons()) {
            for (Holding patHld : patron.holdingMap()) {
                if (holding.getBarcode().equals(patHld.getBarcode()))
                    return patron;
            }
        }
        throw new InvalidPatronIdException();
    }

    private boolean isLateReturn(Holding holding) {
        Date dueDate = holding.dateDue();
        Date checkedInDate = holding.dateLastCheckedIn();
        return checkedInDate != null && dueDate != null && checkedInDate.after(dueDate);
    }

    private int calculateFine(Holding holding, int daysLate, int fineBasis) {
        switch (holding.getMaterial().getFormat()) {
            case BOOK:
            case NEW_RELEASE_DVD:
                return fineBasis * daysLate;
            case DVD:
                return Math.min(1000, 100 + fineBasis * daysLate);
            default:
                return 0;
        }
    }
}
