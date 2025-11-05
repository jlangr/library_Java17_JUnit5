package com.langrsoft.service.library;

import com.langrsoft.domain.*;
import com.langrsoft.external.Material;
import com.langrsoft.persistence.PatronStore;

import java.util.Date;
import java.util.List;

public class HoldingService {
    private final Catalog catalog = new Catalog();

    public String add(String sourceId, String branchId) {
        var branch = findBranch(branchId);
        var holding = new Holding(retrieveMaterialDetails(sourceId), branch);
        catalog.add(holding);
        return holding.getBarcode();
    }

    private Material retrieveMaterialDetails(String sourceId) {
        var material =
                ClassificationApiFactory.getService().retrieveMaterial(sourceId);
        if (material == null)
            throw new InvalidSourceIdException("cannot retrieve material with source ID " + sourceId);
        return material;
    }

    private Branch findBranch(String branchId) {
        var branch = new BranchService().find(branchId);
        if (branch == null)
            throw new BranchNotFoundException("Branch not found: " + branchId);
        return branch;
    }

    public boolean isAvailable(String barCode) {
        var holding = findHoldingForBarcode(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.isAvailable();
    }

    public HoldingMap allHoldings() {
        var stack = new HoldingMap();
        for (var holding : catalog)
            stack.add(holding);
        return stack;
    }

    public Holding findHoldingForBarcode(String barCode) {
        return catalog.find(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return catalog.findByBranch(branchScanCode);
    }

    public void transfer(String barcode, String branchScanCode) {
        Holding holding = findHoldingForBarcode(barcode);
        if (holding == null)
            throw new HoldingNotFoundException();
        Branch branch = new BranchService().find(branchScanCode);
        holding.transfer(branch);
    }

    public Date dateDue(String barCode) {
        Holding holding = findHoldingForBarcode(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding.dateDue();
    }

    public void checkOut(String patronId, String barCode, Date date) {
        Holding holding = findHoldingForBarcode(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
        holding.checkOut(date);

        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    @SuppressWarnings("all")
    // remove warning suppression once refactored
    // Note that this method is missing some coverage, which is typical
    // for a method of this length
    public int checkIn(String barCode, Date date, String branchScanCode) {
        Holding holding = findHoldingForBarcode(barCode);
        if (holding == null) {
            throw new HoldingNotFoundException();
        }
        updateHoldingReturnStatus(date, branchScanCode, holding);
        int daysLate =  getLateDaysForCheckin(holding);
        removeHoldingFromPatronAndHandleFine(holding, daysLate);

        return daysLate;
    }

    private static void updateHoldingReturnStatus(Date date, String branchScanCode, Holding holding) {
        // set the holding to returned status
        Branch branch = new BranchService().find(branchScanCode);
        holding.checkIn(date, branch);
    }

    private static int getLateDaysForCheckin(Holding holding) {
        boolean isLate = holding.dateLastCheckedIn().after(holding.dateDue());

        if (isLate) {
            // calculate # of days past due
            return holding.daysLate();
        }
        return 0;
    }

    private static void handleFineForLateCheckin(Holding holding, Patron matchingPatron, int daysLate) {
        int fineBasis = holding.getMaterial().getFormat().getDailyFine();
        int fine = switch (holding.getMaterial().getFormat()) {
            case BOOK, NEW_RELEASE_DVD -> fineBasis * daysLate;
            case DVD -> Math.min(1000, 100 + fineBasis * daysLate);
            default -> 0;
        };
        matchingPatron.addFine(fine);
    }

    private static void removeHoldingFromPatronAndHandleFine(Holding holding, int daysLate) {
        // locate the patron with the checked out book
        // could introduce a patron reference ID in the holding...
        for (Patron patron : new PatronService().allPatrons()) {
            HoldingMap holdings = patron.holdingMap();
            for (Holding patronHolding : holdings) {
                if (holding.getBarcode().equals(patronHolding.getBarcode())) {
                    handleFineForLateCheckin(holding, patron, daysLate);
                    patron.remove(holding);
                }
            }
        }
    }
}