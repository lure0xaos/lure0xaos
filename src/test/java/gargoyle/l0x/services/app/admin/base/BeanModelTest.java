package gargoyle.l0x.services.app.admin.base;

import gargoyle.l0x.model.app.CreationModel;
import gargoyle.l0x.util.Rnd;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class BeanModelTest {

    @Test
    public void field() {
    }

    @Test
    public void path() {
        CreationModel object = Rnd.randomObject(CreationModel.class);
        Object username = BeanModel.of(CreationModel.class).path(object, "author.username");
        Assertions.assertNotNull(username, "");
    }

    @Test
    public void of() {
    }
}
