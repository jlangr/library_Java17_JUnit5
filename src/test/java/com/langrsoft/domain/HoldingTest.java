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
    Holding holding_All;
    static final Date TODAY = new Date();
    static final int COPY_NUMBER_1 = 1;

    Branch eastBranch = new Branch("East");
    Branch westBranch = new Branch("West");

    @BeforeEach
    void setUp() {

        holding_All = new Holding(THE_TRIAL, eastBranch, COPY_NUMBER_1);
    }

    @Test
    void branchDefaultsToCheckedOutWhenCreated() {
        var holding = new Holding(THE_TRIAL);

        assertThat(holding.getBranch()).isEqualTo(Branch.CHECKED_OUT);
    }

    @Test
    void copyNumberDefaultsToOneWhenCreated() {
        var holding = new Holding(THE_TRIAL, eastBranch);

        assertThat(holding.getCopyNumber()).isEqualTo(1);
    }

    @Test
    void canSetCopyNumber() {
        holding_All.setCopyNumber(2);

        assertThat(holding_All.getCopyNumber()).isEqualTo(2);
    }

    @Test
    void changesBranchOnTransfer() {
        holding_All.transfer(westBranch);

        assertThat(holding_All.getBranch()).isEqualTo(westBranch);
    }

    @Test
    void checkOutAndCheckInFlow() {
        holding_All.checkOut(TODAY);

        assertThat(holding_All.dateCheckedOut()).isEqualTo(TODAY);
        assertThat(holding_All.dateDue()).isAfter(TODAY);
        assertThat(holding_All.getBranch()).isEqualTo(Branch.CHECKED_OUT);
        assertThat(holding_All.isAvailable()).isFalse();

        Date tomorrow = new Date(TODAY.getTime() + 60L + 60 * 1000 * 24);
        holding_All.checkIn(tomorrow, eastBranch);
        assertThat(holding_All.dateLastCheckedIn()).isEqualTo(tomorrow);
        assertThat(holding_All.isAvailable()).isTrue();
        assertThat(holding_All.getBranch()).isEqualTo(eastBranch);
    }

    @Test
    void returnDateForStandardBook() {
        holding_All.checkOut(TODAY);
        var dateDue = holding_All.dateDue();
        assertDateEquals(addDays(TODAY, MaterialType.BOOK.getCheckoutPeriod()), dateDue);
    }

    @Test
    void dateDueNullWhenCheckedOutIsNull() {
        assertThat(holding_All.dateDue()).isNull();
    }

    @Test
    void daysLateIsZeroWhenDateDueIsNull() {
        assertThat(holding_All.daysLate()).isEqualTo(0);
    }

    @Test
    void dateDueForVariousMaterialTypes() {
        checkOutToday(DR_STRANGELOVE, eastBranch);
        assertDateEquals(addDays(TODAY, MaterialType.DVD.getCheckoutPeriod()), holding_All.dateDue());

        checkOutToday(THE_REVENANT, eastBranch);
        assertDateEquals(addDays(TODAY, MaterialType.NEW_RELEASE_DVD.getCheckoutPeriod()), holding_All.dateDue());
    }

    @Test
    void answersDaysLateOfZeroWhenReturnedSameDay() {
        checkOutToday(THE_TRIAL, eastBranch);
        var daysLate = holding_All.checkIn(TODAY, eastBranch);
        assertThat(daysLate).isEqualTo(0);
    }

    @Test
    void answersDaysLateOfZeroWhenReturnedOnDateDue() {
        checkOutToday(THE_TRIAL, eastBranch);
        int daysLate = holding_All.checkIn(holding_All.dateDue(), eastBranch);
        assertThat(daysLate).isEqualTo(0);
    }

    @Test
    void answersDaysLateWhenReturnedAfterDueDate() {
        checkOutToday(THE_TRIAL, eastBranch);
        Date date = addDays(holding_All.dateDue(), 3);
        int days = holding_All.checkIn(date, eastBranch);
        assertThat(days).isEqualTo(3);
    }

    private void checkOutToday(Material material, Branch branch) {
        holding_All = new Holding(material, branch);
        holding_All.checkOut(TODAY);
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