package gargoyle.l0x.services.sessions;

import gargoyle.l0x.dto.session.SpringSessionDto;
import gargoyle.l0x.dto.user.UserDto;

import java.util.List;
import java.util.Map;

public interface Sessions {
    Map<UserDto, List<SpringSessionDto>> getSessions();

    long online();

    void killByUsername(String username);
}
