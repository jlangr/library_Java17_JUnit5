package com.langrsoft.slides;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnAuto {
    @Test
    void idlesEngineWhenStarted() {
        var auto = new Auto();
        auto.DepressBrake();

        auto.PressStartButton();

        assertThat(auto.RPM()).isGreaterThan(950).isLessThan(1100);
    }

    static class Auto {
        public void DepressBrake() {
        }

        public void PressStartButton() {
        }

        public int RPM() {
            return 1000;
        }
    }
}
