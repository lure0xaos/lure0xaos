package gargoyle.l0x.util;

import gargoyle.l0x.model.user.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class RndObjTest {

    @Test
    public void randomObject() {
        UserModel object = Rnd.randomObject(UserModel.class);
        Assertions.assertEquals("", "", "");
    }
}
