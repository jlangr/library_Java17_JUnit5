package com.langrsoft.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static com.langrsoft.util.DateUtil.*;
import static java.util.Calendar.*;
import static org.assertj.core.api.Assertions.assertThat;

class DateUtilTest {
    static final Date NEW_YEARS_DAY = DateUtil.create(2011, JANUARY, 1);

    @Test
    void createGeneratedProperDateElements() {
        var calendar = Calendar.getInstance();

        calendar.setTime(NEW_YEARS_DAY);

        assertThat(calendar.get(YEAR)).isEqualTo(2011);
        assertThat(calendar.get(MONTH)).isEqualTo(JANUARY);
        assertThat(calendar.get(DAY_OF_MONTH)).isEqualTo(1);
        assertThat(calendar.get(HOUR_OF_DAY)).isEqualTo(0);
        assertThat(calendar.get(MINUTE)).isEqualTo(0);
        assertThat(calendar.get(SECOND)).isEqualTo(0);
        assertThat(calendar.get(MILLISECOND)).isEqualTo(0);
    }

    @Test
    void addDaysAnswersLaterDate() {
        assertThat(addDays(create(2017, MARCH, 1), 21)).isEqualTo(DateUtil.create(2017, MARCH, 22));
        assertThat(addDays(NEW_YEARS_DAY, 367)).isEqualTo(DateUtil.create(2012, JANUARY, 3));
        assertThat(addDays(create(2017, DECEMBER, 31), 32)).isEqualTo(DateUtil.create(2018, FEBRUARY, 1));
    }

    @Test
    void answersDaysFromWithinSameYear() {
        var laterBy15 = addDays(NEW_YEARS_DAY, 15);

        assertThat(daysFrom(NEW_YEARS_DAY, laterBy15)).isEqualTo(15);
    }

    @Test
    void answersDaysFromToNextYear() {
        var laterBy375 = addDays(NEW_YEARS_DAY, 375);

        assertThat(daysFrom(NEW_YEARS_DAY, laterBy375)).isEqualTo(375);
    }

    @Test
    void answersDaysFromManyYearsOut() {
        var later = addDays(NEW_YEARS_DAY, 2100);

        assertThat(daysFrom(NEW_YEARS_DAY, later)).isEqualTo(2100);
    }

    @Test
    void convertsJavaUtilDateToLocalDate() {
        var converted = toLocalDate(create(2016, MAY, 15));

        assertThat(converted.getDayOfMonth()).isEqualTo(15);
        assertThat(converted.getYear()).isEqualTo(2016);
        assertThat(converted.getMonth()).isEqualTo(Month.MAY);
    }

    @Test
    void getCurrentDateReturnsInjectedValue() {
        fixClockAt(NEW_YEARS_DAY);

        var date = getCurrentDate();

        assertThat(date).isEqualTo(NEW_YEARS_DAY);
    }

    @Test
    void getCurrentLocalDateReturnsInjectedValue() {
        fixClockAt(NEW_YEARS_DAY);

        var date = getCurrentLocalDate();

        assertThat(date).isEqualTo(toLocalDate(NEW_YEARS_DAY));
    }

    @Test
    void ageInYearsDeterminesYearsBetweenTwoLocalDates() {
        var age = ageInYears(LocalDate.of(2010, Month.MAY, 1), LocalDate.of(2015, Month.MAY, 2));

        assertThat(age).isEqualTo(5);
    }

    @Nested
    class DaysAfter {
        Date janOne2025 = create(2025, JANUARY, 1);

        @Test
        void isZeroWhenSecondDayNotAfterFirst() {
            assertThat(daysAfter(janOne2025, create(2025, JANUARY, 1))).isEqualTo(0);
        }

        @Test
        void whenDayWithinSameYear() {
            assertThat(DateUtil.daysAfter(janOne2025, create(2025, JANUARY, 31))).isEqualTo(30);
        }

        @Test
        void whenInSubsequentYear() {
            assertThat(DateUtil.daysAfter(janOne2025, create(2026, JANUARY, 1))).isEqualTo(365);
        }

        @Test
        void whenYearsFromFirstDay() {
            assertThat(DateUtil.daysAfter(janOne2025, create(2028, JANUARY, 1))).isEqualTo(1095);
        }

        @Test
        void accountsForLeapDay() {
            assertThat(DateUtil.daysAfter(create(2024, JANUARY, 1),
               DateUtil.create(2025, JANUARY, 1))).isEqualTo(366);
        }
    }

    @Nested
    class Tomorrow {
        @Test
        void isAlwaysADayAway() {
            assertThat(toLocalDate(tomorrow()))
               .isEqualTo(toLocalDate(addDays(new Date(), 1)));
        }
    }

    @Nested
    class MockUse {
        @Test
        void currentDateFromFixedClockReturnsInjectedTime() {
            var ides2025 = create(2025, MARCH, 17);

            fixClockAt(ides2025);

            assertThat(getCurrentDate()).isEqualTo(ides2025);
        }

        @Test
        void currentDateAfterResetClockReturnsRealTime() {
            var ides2025 = create(2025, MARCH, 17);
            fixClockAt(ides2025);

            resetClock();

            assertThat(DateUtil.getCurrentDate()).isNotEqualTo(ides2025);
        }
    }
}