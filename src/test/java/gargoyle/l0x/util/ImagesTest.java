package gargoyle.l0x.util;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ImagesTest {

    @Test
    public void fit() {
        Rectangle inner = new Rectangle(10, 20);
        Rectangle outer = new Rectangle(10, 5);
        Rectangle result = Images.fit(inner, outer);
        Assertions.assertEquals(result.getWidth() / result.getHeight(), inner.getWidth() / inner.getHeight(), 0.1, "bad ratio");
        MatcherAssert.assertThat("bad width", result.width, lessThanOrEqualTo(outer.width));
        MatcherAssert.assertThat("bad height", result.height, lessThanOrEqualTo(outer.height));
    }
}
