package com.langrsoft.reporting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InventoryReportTest {
    @Test
    void something() {
        assertThat(new InventoryReport(null)).isNotNull();
    }
}