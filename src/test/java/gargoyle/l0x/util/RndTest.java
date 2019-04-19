package gargoyle.l0x.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;

public class RndTest {
    @Before
    public void setUp() {

    }

    @Test
    public void testRndInt() {
        for (int i = 0; i < 1000; i++) {
            int rnd = Rnd.rnd(-10, 10);
            Assert.assertThat("failed: " + rnd, rnd >= -10 && rnd < 10, is(true));
        }
    }

    @Test
    public void testRndArray() {
        for (int i = 0; i < 1000; i++) {
            Object[] arr = new Object[10];
            Arrays.setAll(arr, value -> value);
            Object rnd = Rnd.rnd(arr);
            Assert.assertThat("failed: " + rnd, rnd != null, is(true));
        }
    }

}