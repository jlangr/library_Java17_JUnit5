package com.langrsoft.service.library;

import com.langrsoft.domain.*;
import com.langrsoft.external.Material;
import com.langrsoft.persistence.PatronStore;

import java.util.Date;
import java.util.List;

public class HoldingService {
   private final Catalog catalog = new Catalog();
   private PatronService patronService = new PatronService();

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
      var holding = findHolding(barCode);
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

   public Holding findHolding(String barCode) {
      return catalog.find(barCode);
   }

   public List<Holding> findByBranch(String branchScanCode) {
      return catalog.findByBranch(branchScanCode);
   }

   public void transfer(String barcode, String branchScanCode) {
      Holding holding = findHolding(barcode);
      if (holding == null)
         throw new HoldingNotFoundException();
      Branch branch = new BranchService().find(branchScanCode);
      holding.transfer(branch);
   }

   public Date dateDue(String barCode) {
      Holding holding = findHolding(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();
      return holding.dateDue();
   }

   public void checkOut(String patronId, String barCode, Date date) {
      Holding holding = findHolding(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();
      if (!holding.isAvailable())
         throw new HoldingAlreadyCheckedOutException();
      holding.checkOut(date);

      PatronStore patronAccess = new PatronStore();
      Patron patron = patronAccess.find(patronId);
      patronAccess.addHoldingToPatron(patron, holding);
   }

   public int checkIn(String barCode, Date date, String branchScanCode) {
      var holding = findHolding(barCode);
      if (holding == null)
         throw new HoldingNotFoundException();

      holding.checkIn(date, getBranch(branchScanCode));

      var patron = patronService.patronWithCheckedOutHolding(holding);
      patron.remove(holding);

      if (holding.isLate())
         applyFine(holding, patron);
      return holding.daysLate();
   }

   private Branch getBranch(String branchScanCode) {
      return new BranchService().find(branchScanCode);
   }

   private void applyFine(Holding holding, Patron patron) {
      int fineBasis = holding.getMaterial().getFormat().getDailyFine();
      switch (holding.getMaterial().getFormat()) {
         case BOOK:
            patron.addFine(fineBasis * holding.daysLate());
            break;
         case DVD:
            int fine = Math.min(1000, 100 + fineBasis * holding.daysLate());
            patron.addFine(fine);
            break;
         case NEW_RELEASE_DVD:
            patron.addFine(fineBasis * holding.daysLate());
            break;
      }
   }

}