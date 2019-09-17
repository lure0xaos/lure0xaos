package gargoyle.l0x.services.away;

import java.text.MessageFormat;

import static gargoyle.l0x.services.away.AwayConstants.LINK_PATTERN;
import static gargoyle.l0x.services.away.AwayUtil.escapeHtml;
import static gargoyle.l0x.services.away.AwayUtil.replaceAll;

@FunctionalInterface
public interface Away {

    String rewrite(String url);

    default String rewriteText(String text) {
        return replaceAll(text, LINK_PATTERN, matcher -> rewrite(matcher.group(0)));
    }

    default String rewriteHtml(String text) {
        return replaceAll(text, LINK_PATTERN, matcher ->
        {
            String link = matcher.group(0);
            return MessageFormat.format(AwayConstants.LINK_HTML, link, escapeHtml(rewrite(link)));
        });
    }
}
