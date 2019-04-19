package gargoyle.l0x.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Getter
@EqualsAndHashCode(of = {"parent", "id"})
@ToString(of = {"id", "alias", "label"})
public class MenuItem implements Comparable<MenuItem> {
    private final Set<MenuItem> children = new TreeSet<>();
    private final String id;
    private final String label;
    private final String title;
    private final String alias;
    private final boolean leaf;
    private boolean visible;
    private MenuItem parent;

    public MenuItem(MenuItem parent, String text, String alias, boolean leaf, boolean visible) {
        this.leaf = leaf;
        this.visible = visible;
        id = parent == null ? alias : String.join("_", parent.id, alias);
        label = text;
        title = text;
        //noinspection AssignmentToNull
        this.alias = leaf ? alias : null;
        this.parent = parent;
    }

    public static MenuItem addRoot(Collection<MenuItem> roots, MenuItem item) {
        for (MenuItem root : roots)
            if (Objects.equals(root, item)) {
                return root;
            }
        roots.add(item);
        return item;
    }

    public MenuItem addChild(MenuItem item) {
        for (MenuItem child : children) {
            if (Objects.equals(child, item)) {
                return child;
            }
        }
        item.parent = this;
        children.add(item);
        return item;
    }

    public void show() {
        MenuItem cur = this;
        while (cur != null) {
            cur.visible = true;
            cur = cur.parent;
        }
    }

    @Override
    public int compareTo(MenuItem o) {
        return id.compareToIgnoreCase(o.id);
    }
}
