package gargoyle.l0x.services.gravatar;

import java.util.Map;

public interface Gravatar {
    String getAvatarLocation(String email);

    String getProfileLocation(String email);

    String getHtmlProfile(String email);

    String getJsonStringProfile(String email);

    Map<String, Object> getJsonProfile(String email);

    String getProfile(String email, String type);
}
