package com.langrsoft.reporting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReportMailerTest {
    @Test
    void stub() {
        try {
            new ReportMailer(new MailDestination[] {});
        }
        catch (Exception expected) {
            assertThat(expected).isNotNull();
        }
    }
}