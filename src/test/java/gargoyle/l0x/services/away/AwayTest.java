package gargoyle.l0x.services.away;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

@Slf4j
public class AwayTest {
    private Away away;

    @BeforeEach
    public void setUp() {
        away = new AwayImpl();
    }

    @Test
    public void testUrl() {
        String msg = away.rewrite("http://www.google.com");
        MatcherAssert.assertThat("", msg, containsString("http%3A%2F%2Fwww.google.com"));
        log.warn(msg);
    }

    @Test
    public void testText() {
        String msg = away.rewriteText("hello url http://www.google.com url");
        MatcherAssert.assertThat("", msg, containsString("http%3A%2F%2Fwww.google.com"));
        log.warn(msg);
    }

    @Test
    public void testHtml() {
        String msg = away.rewriteHtml("hello url http://www.google.com url");
        MatcherAssert.assertThat("", msg, containsString("http%3A%2F%2Fwww.google.com"));
        log.warn(msg);
    }
}
