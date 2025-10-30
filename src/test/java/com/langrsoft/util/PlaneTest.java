package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PlaneTest {
    Plane plane = new Plane();

    @Test
    void multipleSimple() {
        var points = List.of(
           new Point(3, 3),
           new Point(5, -1),
           new Point(-2, 4),
           new Point(40, 5));

        assertThat(plane.kClosestPoints(points, 2))
           .containsExactly(new Point(3, 3), new Point(-2, 4));
    }
}