package com.langrsoft.service.library;

import com.langrsoft.domain.*;
import com.langrsoft.external.Material;
import com.langrsoft.persistence.PatronStore;

import java.util.Calendar;
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
        var holding = find(barCode);
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

    public Holding find(String barCode) {
        return catalog.find(barCode);
    }

    public List<Holding> findByBranch(String branchScanCode) {
        return catalog.findByBranch(branchScanCode);
    }

    public void transfer(String barcode, String branchScanCode) {
        Holding holding = getValidHolding(barcode);
        Branch branch = new BranchService().find(branchScanCode);
        holding.transfer(branch);
    }

    public Date dateDue(String barCode) {
        Holding holding = getValidHolding(barCode);
        return holding.dateDue();
    }

    public void checkOut(String patronId, String barCode, Date date) {
        Holding holding = getValidHolding(barCode);
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
        holding.checkOut(date);

        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    @SuppressWarnings("all") // remove warning suppression once refactored
    // Note that this method is missing some coverage, which is typical
    // for a method of this length
    public int checkIn(String barCode, Date date, String branchScanCode) {
        Branch branch = new BranchService().find(branchScanCode);
        Holding holding = getValidHolding(barCode);

        // set the holding to returned status
        holding.checkIn(date, branch);

        // locate the patron with the checked out book
        // could introduce a patron reference ID in the holding...
        Patron patron = locatePatron(holding);

        // remove the book from the patron
        patron.remove(holding);

        // check for late returns
        boolean isLate = false;
        isLate = isLateReturn(holding, isLate);
        if (isLate) {
            return getNumberOfDaysLate(holding, patron);
        }
        return 0;
    }

    private static int getNumberOfDaysLate(Holding holding, Patron patron) {
        int daysLate = holding.daysLate(); // calculate # of days past due
        int fineBasis = holding.getMaterial().getFormat().getDailyFine();
        switch (holding.getMaterial().getFormat()) {
            case BOOK:
                patron.addFine(fineBasis * daysLate);
                break;
            case DVD:
                int fine = Math.min(1000, 100 + fineBasis * daysLate);
                patron.addFine(fine);
                break;
            case NEW_RELEASE_DVD:
                patron.addFine(fineBasis * daysLate);
                break;
        }
        return daysLate;
    }

    private static boolean isLateReturn(Holding holding, boolean isLate) {
        Calendar c = Calendar.getInstance();
        c.setTime(holding.dateDue());

        if (holding.dateLastCheckedIn().after(c.getTime())) // is it late?
            isLate = true;
        return isLate;
    }

    private static Patron locatePatron(Holding holding) {
        HoldingMap holdings;
        Patron patron = null;
        for (Patron p : new PatronService().allPatrons()) {
            holdings = p.holdingMap();
            for (Holding patHld : holdings) {
                if (holding.getBarcode().equals(patHld.getBarcode()))
                    patron = p;
            }
        }
        return patron;
    }

    private Holding getValidHolding(String barCode) {
        Holding holding = find(barCode);
        if (holding == null)
            throw new HoldingNotFoundException();
        return holding;
    }
}