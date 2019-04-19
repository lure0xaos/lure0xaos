package gargoyle.l0x.services.app.admin.base;

import gargoyle.l0x.dto.app.CreationDto;
import gargoyle.l0x.util.Rnd;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BeanModelTest {

    @Test
    public void field() {
    }

    @Test
    public void path() {
        CreationDto object = Rnd.randomObject(CreationDto.class);
        Object username = BeanModel.of(CreationDto.class).path(object, "author.username");
        assertNotNull("", username);
    }

    @Test
    public void of() {
    }
}