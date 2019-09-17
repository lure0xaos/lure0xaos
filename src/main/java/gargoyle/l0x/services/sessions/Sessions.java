package gargoyle.l0x.services.sessions;

import gargoyle.l0x.model.session.SpringSessionModel;
import gargoyle.l0x.model.user.UserModel;

import java.util.List;
import java.util.Map;

public interface Sessions {
    Map<UserModel, List<SpringSessionModel>> getSessions();

    long online();

    void killByUsername(String username);
}
