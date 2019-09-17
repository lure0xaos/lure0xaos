package gargoyle.l0x.services.away;

import java.util.regex.Pattern;

public enum AwayConstants {
    ;
    public static final Pattern LINK_PATTERN = Pattern.compile("(?:(?:https?://)|(?:www\\.)|(?:https?://www\\.))(?:[.-?=%#&+a-zA-Z0-9]+)");
    public static final String LINK_HTML = "<a href=\"{0}\" target=\"_blank\">{1}</a>";

    public static final String PARAM_URL = "url";
    public static final String LINK_PATH = "to";
}
