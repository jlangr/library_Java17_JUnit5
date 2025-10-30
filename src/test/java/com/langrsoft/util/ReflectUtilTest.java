package com.langrsoft.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReflectUtilTest {
    ReflectUtil reflectUtil = new ReflectUtil();

    @Nested
    class MapAccessorToString {
        @Test
        void extractsValueAndCasts() throws NoSuchMethodException {
            class Sample {
                final String name;
                Sample(String name) { this.name = name; }
                public String getName() { return name; }
            }

            var method = Sample.class.getMethod("getName");
            var object = new Sample("smelt");

            var result = reflectUtil.mapAccessorToString(method, object, String.class);

            assertThat(result).isEqualTo("smelt");
        }

        @Test
        void rethrowsInvocationTargetException() throws NoSuchMethodException {
            class CannotRun {
                public int getN() { throw new IllegalArgumentException(); }
            }
            var badMethod = CannotRun.class.getMethod("getN");
            var object = new CannotRun();

            assertThrows(ReflectException.class, () ->
               reflectUtil.mapAccessorToString(badMethod, object, String.class));
        }

        @Test
        void rethrowsIllegalAccessException() throws NoSuchMethodException {
            class Sample {
                private String getName() { return "x"; }
            }
            var method = Sample.class.getDeclaredMethod("getName");
            var object = new Sample();

            assertThrows(ReflectException.class, () ->
               reflectUtil.mapAccessorToString(method, object, String.class));
        }
    }

    @Nested
    class GetMethod {
        @Test
        void returnsCorrespondingMethodObject() {
            class Sample {
                @SuppressWarnings("unused")
                public void myMethod() { }
            }

            var method = reflectUtil.getMethod("myMethod", Sample.class);

            assertThat(method.getName()).isEqualTo("myMethod");
        }

        @Test
        void rethrowsNoSuchMethodException() {
            assertThrows(ReflectException.class,
               () -> reflectUtil.getMethod("nope", String.class));
        }
    }
}
