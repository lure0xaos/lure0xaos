package gargoyle.l0x.entities.app.base;

import gargoyle.l0x.entities.user.User;

public interface OwnerEntity {
    User getAuthor();

    void setAuthor(User author);
}
