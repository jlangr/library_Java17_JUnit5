package com.langrsoft.service.library;

import com.langrsoft.domain.State;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateServiceTest {
    StateService service = new StateService();

    @Test
    void findNameStartingWithReturnsEmptyWhenNotFound() {
        assertThat(service.findNameStartingWith("Xyz")).isEmpty();
    }

    @Test
    void findNameStartingWithReturnsSingleElement() {
        assertThat(service.findNameStartingWith("Col"))
           .containsExactly(new State("Colorado", "Denver"));
    }

    @Test
    void findNameStartingWithReturnsMultipleElements() {
        assertThat(service.findNameStartingWith("Te"))
           .containsExactlyInAnyOrder(
              new State("Texas", "Austin"),
              new State("Tennessee", "Nashville")
           );
    }
}