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
        checkAvailabilityOfHolding(holding);
        holding.checkOut(date);

        addHoldingToPatron(patronId, holding);
    }

    private static void checkAvailabilityOfHolding(Holding holding) {
        if (!holding.isAvailable())
            throw new HoldingAlreadyCheckedOutException();
    }

    private static void addHoldingToPatron(String patronId, Holding holding) {
        PatronStore patronAccess = new PatronStore();
        Patron patron = patronAccess.find(patronId);
        patronAccess.addHoldingToPatron(patron, holding);
    }

    @SuppressWarnings("all")
    public int checkIn(String barCode, Date date, String branchScanCode) {
        Branch branch = new BranchService().find(branchScanCode);
        Holding holding = getValidHolding(barCode);
        holding.checkIn(date, branch);
        Patron patron = locatePatron(holding);
        patron.remove(holding);
        return getNumberOfDaysLate(holding, patron);
    }

    private static int getNumberOfDaysLate(Holding holding, Patron patron) {

        int daysLate = 0;
        boolean isLate = isLateReturn(holding);
        if (isLate) {
            daysLate = holding.daysLate(); // calculate # of days past due
            int fineBasis = holding.getMaterial().getFormat().getDailyFine();
            switch (holding.getMaterial().getFormat()) {
                case NEW_RELEASE_DVD:
                case BOOK:
                    patron.addFine(fineBasis * daysLate);
                    break;
                case DVD:
                    int fine = Math.min(1000, 100 + fineBasis * daysLate);
                    patron.addFine(fine);
                    break;
            }
        }
        return daysLate;
    }

    private static boolean isLateReturn(Holding holding) {
        Calendar c = Calendar.getInstance();
        boolean isLate = false;
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