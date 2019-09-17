package gargoyle.l0x.model.app.base;

import gargoyle.l0x.model.user.UserModel;

public interface OwnerModel {
    UserModel getAuthor();

    void setAuthor(UserModel author);
}
