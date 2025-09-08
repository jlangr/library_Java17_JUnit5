package com.langrsoft.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TimestampSourceTest {
    static final Date NEW_YEARS_DAY = DateUtil.create(2011, Calendar.JANUARY, 1);

    @AfterEach
    void clearTimestampSource() {
        TimestampSource.emptyQueue();
    }

    @Test
    void retrievesSinglePushedTime() {
        TimestampSource.queueNextTime(NEW_YEARS_DAY);

        assertThat(TimestampSource.now()).isEqualTo(NEW_YEARS_DAY);
    }

    @Test
    void retrievesMultiplePushedTimes() {
        var groundhogDay = DateUtil.create(2011, Calendar.FEBRUARY, 2);
        TimestampSource.queueNextTime(NEW_YEARS_DAY);
        TimestampSource.queueNextTime(groundhogDay);

        assertThat(TimestampSource.now()).isEqualTo(NEW_YEARS_DAY);
        assertThat(TimestampSource.now()).isEqualTo(groundhogDay);
    }

    @Test
    void isNotExhaustedWhenTimeQueued() {
        TimestampSource.queueNextTime(NEW_YEARS_DAY);
        assertThat(TimestampSource.isExhausted()).isFalse();
    }

    @Test
    void isExhaustedWhenNoTimeQueued() {
        assertThat(TimestampSource.isExhausted()).isTrue();
        TimestampSource.queueNextTime(NEW_YEARS_DAY);
        TimestampSource.now();
        assertThat(TimestampSource.isExhausted()).isTrue();
    }

    @Test
    void returnsCurrentTimeWhenQueueExhausted() {
        TimestampSource.queueNextTime(NEW_YEARS_DAY);

        var now = new Date();
        TimestampSource.now();
        var retrievedNow = TimestampSource.now();
        assertThat(retrievedNow.getTime() - now.getTime()).isLessThan(100);
    }
}