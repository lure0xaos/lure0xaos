package gargoyle.l0x.services.away;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service(AwayImpl.BEAN_ID)
public class AwayImpl implements Away {

    public static final String BEAN_ID = "away";

    @Override
    public String rewrite(String url) {
        return "/to/?url=" + URLEncoder.encode(url, StandardCharsets.UTF_8);
    }
}
