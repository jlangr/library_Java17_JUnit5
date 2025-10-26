package com.langrsoft.domain;

import com.langrsoft.external.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.langrsoft.util.DateUtil.addDays;
import static org.junit.jupiter.api.Assertions.*;

class HoldingIsLateTest {
   Holding holding;
   Material material = new Material("1", "X", "X", "x", "1877");
   Branch branch = new Branch("east");

   @BeforeEach
   void setUp() {
      holding = new Holding(material);
   }

   @Test
   void isLateWhenCheckedInAfterDueDate() {
      holding.checkOut(new Date());

      holding.checkIn(addDays(holding.dateDue(), 1), branch);

      assertTrue(holding.isLate());
   }

   @Test
   void isNotLateWhenCheckedInBeforeDueDate() {
      holding.checkOut(new Date());

      holding.checkIn(addDays(holding.dateDue(), -1), branch);

      assertFalse(holding.isLate());
   }

   @Test
   void isNotLateWhenCheckedInOnDueDate() {
      holding.checkOut(new Date());

      holding.checkIn(holding.dateDue(), branch);

      assertFalse(holding.isLate());
   }
}