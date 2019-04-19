package gargoyle.l0x.util;

import gargoyle.l0x.dto.user.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RndObjTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void randomObject() {
        UserDto object = Rnd.randomObject(UserDto.class);
        assertEquals("", "", "");
    }
}