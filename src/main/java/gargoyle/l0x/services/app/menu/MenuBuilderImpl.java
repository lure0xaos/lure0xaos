package gargoyle.l0x.services.app.menu;

import gargoyle.l0x.data.MenuItem;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.services.app.App;
import gargoyle.l0x.util.Aliases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service(MenuBuilderImpl.BEAN_ID)
@RequiredArgsConstructor
@Slf4j
public class MenuBuilderImpl implements MenuBuilder {
    public static final String BEAN_ID = "menuBuilder";
    private final App app;
    private final MessageSource messages;

    @Override
    public Set<MenuItem> buildMenu(String selectedAlias) {
        Set<MenuItem> roots = new TreeSet<>();
        for (Creation creation : app.findCreations(Sort.by(Sort.Direction.ASC, "author", "date", "genre", "alias"))) {
            boolean visible = creation.getAlias().equals(selectedAlias);
            String author = creation.getAuthor().getUsername();
            MenuItem authorMenuItem = MenuItem.addRoot(roots, new MenuItem(null, author, Aliases.toAlias(author), false, visible));
            String year = String.valueOf(creation.getDate().getYear());
            MenuItem yearMenuItem = authorMenuItem.addChild(new MenuItem(authorMenuItem, year, year, false, visible));
            String genre = creation.getGenre();
            MenuItem genreMenuItem = yearMenuItem.addChild(new MenuItem(yearMenuItem, messages.getMessage("genre-" + genre, null, genre, null), genre, false, visible));
            MenuItem creationMenuItem = genreMenuItem.addChild(new MenuItem(genreMenuItem, creation.getHead(), creation.getAlias(), true, visible));
            if (visible) {
                creationMenuItem.show();
            }
        }
        return roots;
    }
}
