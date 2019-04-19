package gargoyle.l0x.services.md;

import com.overzealous.remark.Remark;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.stereotype.Service;

import static com.vladsch.flexmark.html.HtmlRenderer.SOFT_BREAK;
import static com.vladsch.flexmark.html.HtmlRenderer.builder;

@Service(MDImpl.BEAN_ID)
public class MDImpl implements MD {
    public static final String BEAN_ID = "md";
    private final Parser parser;
    private final HtmlRenderer renderer;
    private final Remark remark;

    public MDImpl() {
        MutableDataHolder options = new MutableDataSet();
//        options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));
        options.set(SOFT_BREAK, "<br />\n");
        parser = Parser.builder(options).build();
        renderer = builder(options).build();
        remark = new Remark();
    }

    @Override
    public String toHtml(String source) {
        return renderer.render(parser.parse(source));
    }

    @Override
    public String toSource(String html) {
        return remark.convertFragment(html);
    }
}
