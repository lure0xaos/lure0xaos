package gargoyle.l0x.services.md;

import com.overzealous.remark.Remark;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Service;

import static com.vladsch.flexmark.html.HtmlRenderer.SOFT_BREAK;
import static com.vladsch.flexmark.html.HtmlRenderer.builder;

@Service(MDImpl.BEAN_ID)
public class MDImpl implements MD {
    public static final String BEAN_ID = "md";
    private static final String BR = "<br />\n";
    private final Parser parser;
    private final HtmlRenderer renderer;
    private final Remark remark;

    public MDImpl() {
        MutableDataHolder options = new MutableDataSet();
        options.set(SOFT_BREAK, BR);
        parser = Parser.builder(options).build();
        renderer = builder(options).build();
        remark = new Remark();
    }

    @Override
    public String toHtml(String source) {
        String render = renderer.render(parser.parse(source));
        if (render.startsWith("<p>") && render.startsWith("<p>") && render.indexOf("<p>", 1) < 0)
            return render.substring(3, render.length() - 5);
        return render;
    }

    @Override
    public String toSource(String html) {
        return remark.convertFragment(html);
    }
}
