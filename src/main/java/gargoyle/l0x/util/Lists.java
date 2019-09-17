package gargoyle.l0x.util;

import java.util.Arrays;
import java.util.List;

public class Lists {
    public static <T> List<T> of(T... data) {
        return Arrays.asList(data);
    }
}
