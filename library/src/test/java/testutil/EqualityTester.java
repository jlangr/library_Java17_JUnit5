package testutil;

import static org.assertj.core.api.Assertions.assertThat;

public class EqualityTester {
    final private Object object1;
    final private Object object1Copy1;
    final private Object object1Copy2;
    final private Object object2;
    final private Object object1Subtype;

    public EqualityTester(Object object1, Object object1Copy1,
                          Object object1Copy2, Object object2, Object object1Subtype) {
        this.object1 = object1;
        this.object1Copy1 = object1Copy1;
        this.object1Copy2 = object1Copy2;
        this.object2 = object2;
        this.object1Subtype = object1Subtype;
    }

    public void verify() {
        assertThat(object1).isEqualTo(object1Copy1);
        assertThat(object1).isNotEqualTo(object2);
        assertThat(object1).isNotEqualTo(object1Subtype);
        assertThat(object1).isNotEqualTo(null);

        assertConsistency();
        assertTransitivity();
        assertReflexivity();
        assertSymmetry();
    }

    private void assertConsistency() {
        assertThat(object1).isEqualTo(object1Copy1);
        assertThat(object1).isNotEqualTo(object2);
    }

    private void assertTransitivity() {
        assertThat(object1Copy1).isEqualTo(object1Copy2);
        assertThat(object1).isEqualTo(object1Copy2);
    }

    private void assertSymmetry() {
        assertThat(object1).isEqualTo(object1Copy1);
        assertThat(object1Copy1).isEqualTo(object1);
    }

    @SuppressWarnings("all")
    private void assertReflexivity() {
        assertThat(object1).isEqualTo(object1);
    }
}
