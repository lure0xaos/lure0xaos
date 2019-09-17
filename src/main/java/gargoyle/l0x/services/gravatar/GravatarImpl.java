package gargoyle.l0x.services.gravatar;

import gargoyle.l0x.util.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static gargoyle.l0x.services.gravatar.GravatarConstants.*;

@Service(GravatarImpl.BEAN_ID)
@Slf4j
public class GravatarImpl implements Gravatar {
    public static final String BEAN_ID = "gravatar";
    private static final MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("MagicNumber")
    private static String hash(String email) {
        try {
            return new BigInteger(1, md.digest(email.getBytes(StandardCharsets.UTF_8))).toString(0x10);
        } finally {
            md.reset();
        }
    }

    @Override
    public String getAvatarLocation(String email) {
        return URL_AVATAR + hash(email);
    }

    @Override
    public String getProfileLocation(String email) {
        return URL_PROFILE + hash(email);
    }

    @Override
    public String getHtmlProfile(String email) {
        return getProfile(email, TYPE_HTML);
    }

    @Override
    public String getJsonStringProfile(String email) {
        return getProfile(email, TYPE_JSON);
    }

    @Override
    public Map<String, Object> getJsonProfile(String email) {
        String jsonString = getJsonStringProfile(email);
        try {
            Map<String, Object> data = new JSONParser(jsonString).parseObject();
            return Collections.unmodifiableMap(Objects.requireNonNull(
                    Maps.transform(Maps.<Map<String, Object>>path(
                            data, "entry.0"),
                            o -> o instanceof String ? ((String) o).replace("\\/", "/") : o)));
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage(), e);
            return Collections.emptyMap();
        }
    }

    @Override
    public String getProfile(String email, String type) {
        try {
            return new RestTemplate().getForObject(getProfileLocation(email) + "." + type, String.class);
        } catch (RestClientException e) {
            log.error(e.getLocalizedMessage(), e);
            return "";
        }
    }
}
