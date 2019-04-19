package gargoyle.l0x.services.away;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AwayUtil {
    ;

    public static String escapeHtml(String group) {
        return group
                .replace("&", "&alt;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    public static String replaceAll(CharSequence text, Pattern pattern, Function<Matcher, String> replacement) {
        StringBuffer res = new StringBuffer();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matcher.appendReplacement(res, replacement.apply(matcher));
        }
        matcher.appendTail(res);
        return res.toString();
    }
}
