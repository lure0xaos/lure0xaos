package gargoyle.l0x.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RndTest {

    @Test
    public void testRndInt() {
        for (int i = 0; 1000 > i; i++) {
            int rnd = Rnd.rnd(-10, 10);
            Assertions.assertTrue(-10 <= rnd && 10 > rnd, "failed: " + rnd);
        }
    }

    @Test
    public void testRndArray() {
        for (int i = 0; 1000 > i; i++) {
            Object[] arr = new Object[10];
            Arrays.setAll(arr, value -> value);
            Object rnd = Rnd.rnd(arr);
            Assertions.assertNotNull(rnd, "failed: " + rnd);
        }
    }

}
