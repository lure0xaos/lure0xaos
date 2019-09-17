package gargoyle.l0x.services.app.menu;

import gargoyle.l0x.data.MenuItem;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.err.ItemNotFoundException;
import gargoyle.l0x.services.app.App;
import gargoyle.l0x.util.Aliases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
            boolean visible = Objects.equals(creation.getAlias(), selectedAlias);
            String author = creation.getAuthor().getUsername();
            MenuItem authorMenuItem = MenuItem.createRoot(roots,
                    new MenuItem(null, author, Aliases.toAlias(author), false, visible));
            String year = String.valueOf(creation.getDate().getYear());
            MenuItem yearMenuItem = authorMenuItem.assignChild(
                    new MenuItem(authorMenuItem, year, year, false, visible));
            String genre = creation.getGenre();
            MenuItem genreMenuItem = yearMenuItem.assignChild(
                    new MenuItem(yearMenuItem, messages.getMessage("genre-" + genre, null, genre, LocaleContextHolder.getLocale()),
                            genre, false, visible));
            MenuItem creationMenuItem = genreMenuItem.assignChild(
                    new MenuItem(genreMenuItem, creation.getHead(), creation.getAlias(), true, visible));
            if (visible) {
                creationMenuItem.show();
            }
        }
        return roots;
    }

    @Override
    public List<MenuItem> buildBreadcrumb(String selectedAlias) {
        Creation creation = app.findCreation(selectedAlias).orElseThrow(() -> new ItemNotFoundException(selectedAlias));
        String author = creation.getAuthor().getUsername();
        MenuItem authorMenuItem = new MenuItem(null, author, Aliases.toAlias(author), false, true);
        String year = String.valueOf(creation.getDate().getYear());
        MenuItem yearMenuItem = new MenuItem(null, year, year, false, true);
        String genre = creation.getGenre();
        MenuItem genreMenuItem = new MenuItem(null,
                messages.getMessage("genre-" + genre, null, genre, LocaleContextHolder.getLocale()),
                genre, false, true);
        MenuItem creationMenuItem = new MenuItem(null, creation.getHead(), creation.getAlias(), true, true);
        return Arrays.asList(authorMenuItem, yearMenuItem, genreMenuItem, creationMenuItem);
    }
}
