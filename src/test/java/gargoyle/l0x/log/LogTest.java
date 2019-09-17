package gargoyle.l0x.log;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTest {

    @Autowired
    private LoggingService service;

    @Test
    public void testReturn() {
        LogParam param1 = new LogParam("param1", 1);
        LogParam param2 = new LogParam("param2", 2);
        LogResult logResult = service.returnMethod(param1, param2);
        Assert.assertThat(logResult, notNullValue());
    }

    @Test(expected = RuntimeException.class)
    public void testThrow() {
        LogParam param1 = new LogParam("param1", 1);
        LogParam param2 = new LogParam("param2", 2);
        LogResult logResult = service.throwMethod(param1, param2);
        Assert.assertThat(logResult, notNullValue());
    }

}
