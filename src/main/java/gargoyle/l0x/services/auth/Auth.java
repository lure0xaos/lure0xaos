package gargoyle.l0x.services.auth;

import gargoyle.l0x.dto.user.UserDto;
import gargoyle.l0x.entities.user.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface Auth {
    Optional<Authentication> getAuthentication();

    Optional<UserDto> getLoggedUser();

    Optional<User> getRealUser();

    boolean isFullyLogged();
}
