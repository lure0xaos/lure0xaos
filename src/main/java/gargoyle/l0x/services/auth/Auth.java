package gargoyle.l0x.services.auth;

import gargoyle.l0x.entities.user.User;
import gargoyle.l0x.model.user.UserModel;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface Auth {
    Optional<Authentication> getAuthentication();

    Optional<UserModel> getLoggedUser();

    Optional<User> getRealUser();

    boolean isFullyLogged();

    boolean hasRole(String role);
}
