package com.langrsoft.util;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PointTest {
    @Test
    void canMove() {
        assertThat(new Point(0, 0).move(10, 0),
                is(equalTo(new Point(10, 0))));

        assertThat(new Point(0, 0).move(20, 90),
                is(equalTo(new Point(0, 20))));
    }

    @Test
    void pointsAreEqualWhenWithinTolerance() {
        var pointA = new Point (10, 20);
        var pointB = new Point(
                10 + (Point.EQUALS_TOLERANCE - 0.0001),
                20 - (Point.EQUALS_TOLERANCE - 0.0001));

        assertThat(pointA, is(equalTo(pointB)));
    }

    @Test
    void pointsAreUnequalWhenDifferingByMoreThanTolerance() {
        var pointA = new Point(10, 20);
        var pointB = new Point( 10 + (Point.EQUALS_TOLERANCE + 0.0001), 20);

        assertThat(pointA, is(not(equalTo(pointB))));
    }

    @Test
    void hashCodeValue() {
        var a = new Point(1, 2);
        var aCopy = new Point(1, 2);
        var b = new Point(2, 2);

        assertThat(a.hashCode(), equalTo(aCopy.hashCode()));
        assertThat(a.hashCode(), not(equalTo(b.hashCode())));
    }

    @Test
    void toStringValue() {
        assertThat(new Point(3, 4).toString(), equalTo("(3.0, 4.0)"));
    }
}
