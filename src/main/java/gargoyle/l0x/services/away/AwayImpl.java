package gargoyle.l0x.services.away;

import gargoyle.l0x.controllers.away.AwayController;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service(AwayImpl.BEAN_ID)
public class AwayImpl implements Away {

    public static final String BEAN_ID = "away";

    @Override
    public String rewrite(String url) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .pathSegment(AwayConstants.LINK_PATH)
                .queryParam(AwayController.MODEL_URL, URLEncoder.encode(url, StandardCharsets.UTF_8))
                .build().toUriString();
    }
}
