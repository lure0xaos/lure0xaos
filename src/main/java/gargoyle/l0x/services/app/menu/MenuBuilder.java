package gargoyle.l0x.services.app.menu;

import gargoyle.l0x.data.MenuItem;

import java.util.List;
import java.util.Set;

public interface MenuBuilder {
    Set<MenuItem> buildMenu(String selectedAlias);

    List<MenuItem> buildBreadcrumb(String selectedAlias);
}
