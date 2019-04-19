package gargoyle.l0x.services.app.menu;

import gargoyle.l0x.data.MenuItem;

import java.util.Set;

@FunctionalInterface
public interface MenuBuilder {
    Set<MenuItem> buildMenu(String selectedAlias);
}
