package gargoyle.l0x.services.sessions;

import gargoyle.l0x.model.session.SpringSessionModel;
import gargoyle.l0x.model.user.UserModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionsImplTest {

    @Autowired
    private Sessions sessions;

    @Test
    public void getSessions() {
        Map<UserModel, List<SpringSessionModel>> sessionMap = sessions.getSessions();
        Assert.assertThat("", sessionMap, notNullValue());
    }

    @Test
    public void online() {
        Long online = sessions.online();
        Assert.assertThat("", online, notNullValue());
    }
}
