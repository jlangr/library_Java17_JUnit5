package com.langrsoft.domain;

import com.langrsoft.external.Material;
import com.langrsoft.external.MaterialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutil.EqualityTester;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/*
This test class is a mess. Some opportunities for cleanup:

 - AAA used but no visual separation
 - seeming use of AAA, but it's not really
 - unnecessary code (null checks? try/catch?)
 - constant names that obscure relevant information
 - can data be created in the test?
 - poor / inconsistent test names
 - comments in tests (are they even true)?
 - multiple behaviors/asserts per test
 - code in the wrong place / opportunities for reuse of existing code
 - dead code
 */

class HoldingTest {
    static final Material THE_TRIAL = new Material("10", "", "10", "", "");
    static final Material DR_STRANGELOVE = new Material("12", "", "11", "", MaterialType.DVD, "");
    static final Material THE_REVENANT = new Material("12", "", "11", "", MaterialType.NEW_RELEASE_DVD, "");
    Holding initialHolding;
    static final Date TODAY = new Date();
    static final int COPY_NUMBER_1 = 1;
    Branch eastBranch = new Branch("East");
    Branch westBranch = new Branch("West");

    @BeforeEach
    void setUp() {
        initialHolding = new Holding(THE_TRIAL, eastBranch, COPY_NUMBER_1);
    }

    @Test
    void branchDefaultsToCheckedOutWhenCreated() {
        var holding = new Holding(THE_TRIAL);
        assertThat(holding.getBranch()).isEqualTo(Branch.CHECKED_OUT);
    }

    @Test
    void copyNumberDefaultsTo1WhenCreated() {
        var holding = new Holding(THE_TRIAL, eastBranch);
        assertThat(holding.getCopyNumber()).isEqualTo(1);
    }

    @Test
    void updateCopyNumberToDifferentValue() {
        initialHolding.setCopyNumber(2);
        assertThat(initialHolding.getCopyNumber()).isEqualTo(2);
    }

    @Test
    void changesBranchOnTransfer() {
        initialHolding.transfer(westBranch);
        assertThat(initialHolding.getBranch()).isEqualTo(westBranch);
    }

    @Test
    void checkOutFlow() {
        initialHolding.checkOut(TODAY);
        assertThat(initialHolding.dateCheckedOut()).isEqualTo(TODAY);
        assertThat(initialHolding.dateDue()).isAfter(TODAY);
        assertThat(initialHolding.getBranch()).isEqualTo(Branch.CHECKED_OUT);
        assertThat(initialHolding.isAvailable()).isFalse();
    }

    @Test
    void checkInFlow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TODAY);
        // Add one day to the date
        calendar.add(Calendar.DATE, 1);

        // Get the updated time (tomorrow)
        Date tomorrow = calendar.getTime();
        initialHolding.checkIn(tomorrow, eastBranch);
        assertThat(initialHolding.dateLastCheckedIn()).isEqualTo(tomorrow);
        assertThat(initialHolding.isAvailable()).isTrue();
        assertThat(initialHolding.getBranch()).isEqualTo(eastBranch);
    }

    @Test
    void returnDateForStandardBook() {
        initialHolding.checkOut(TODAY);
        var dateDue = initialHolding.dateDue();
        assertDateEquals(addDays(TODAY, MaterialType.BOOK.getCheckoutPeriod()), dateDue);
    }

    @Test
    void dateDueNullWhenCheckedOutIsNull() {
        assertThat(initialHolding.dateDue()).isNull();
    }

    @Test
    void daysLateIsZeroWhenDateDueIsNull() {
        assertThat(initialHolding.daysLate()).isEqualTo(0);
    }

    @Test
    void dateDueForVariousMaterialTypes() {
        checkOutToday(DR_STRANGELOVE, eastBranch);
        assertDateEquals(addDays(TODAY, MaterialType.DVD.getCheckoutPeriod()), initialHolding.dateDue());

        checkOutToday(THE_REVENANT, eastBranch);
        assertDateEquals(addDays(TODAY, MaterialType.NEW_RELEASE_DVD.getCheckoutPeriod()), initialHolding.dateDue());
    }

    @Test
    void answersDaysLateOfZeroWhenReturnedSameDay() {
        checkOutToday(THE_TRIAL, eastBranch);
        var daysLate = initialHolding.checkIn(TODAY, eastBranch);
        assertThat(daysLate).isEqualTo(0);
    }

    @Test
    void answersDaysLateOfZeroWhenReturnedOnDateDue() {
        checkOutToday(THE_TRIAL, eastBranch);
        int daysLate = initialHolding.checkIn(initialHolding.dateDue(), eastBranch);
        assertThat(daysLate).isEqualTo(0);
    }

    @Test
    void answersDaysLateWhenReturnedAfterDueDate() {
        checkOutToday(THE_TRIAL, eastBranch);
        Date date = addDays(initialHolding.dateDue(), 3);
        int days = initialHolding.checkIn(date, eastBranch);
        assertThat(days).isEqualTo(3);
    }

    private void checkOutToday(Material material, Branch branch) {
        initialHolding = new Holding(material, branch);
        initialHolding.checkOut(TODAY);
    }

    static Date addDays(Date date, int days) {
        return new Date(date.getTime() + days * 60L * 1000 * 60 * 24);
    }

    static void assertDateEquals(Date expectedDate, Date actualDate) {
        var calendar = Calendar.getInstance();
        calendar.setTime(expectedDate);
        var expectedYear = calendar.get(Calendar.YEAR);
        var expectedDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(actualDate);
        assertThat(calendar.get(Calendar.YEAR)).isEqualTo(expectedYear);
        assertThat(calendar.get(Calendar.DAY_OF_YEAR)).isEqualTo(expectedDayOfYear);
    }

    Holding holding1 = new Holding(THE_TRIAL, eastBranch, 1);
    Holding holding1Copy1 = new Holding(THE_TRIAL, westBranch, 1);
    Holding holding1Copy2 = new Holding(THE_TRIAL, Branch.CHECKED_OUT, 1);
    Holding holding2 = new Holding(THE_TRIAL, eastBranch, 2);
    Holding holding1Subtype = new Holding(THE_TRIAL, eastBranch, 1) {};

    @Test
    void toStringContainsHoldingAttributes() {
        var s = holding1.toString();
        assertThat(s).contains(THE_TRIAL.getAuthor());
        assertThat(s).contains(holding1.getBranch().getName());
    }

    @Test
    void equality() {
        new EqualityTester(holding1, holding1Copy1, holding1Copy2, holding2, holding1Subtype).verify();
    }

    @Test
    void hashCodes() {
        assertThat(holding1.hashCode()).isEqualTo(holding1Copy1.hashCode());
        assertThat(holding1.hashCode()).isNotEqualTo(holding2.hashCode());
    }
}