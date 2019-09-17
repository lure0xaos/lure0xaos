package gargoyle.l0x.services.auth;

import gargoyle.l0x.entities.user.User;
import gargoyle.l0x.model.user.UserModel;
import gargoyle.l0x.services.users.Users;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static gargoyle.l0x.data.Roles.ANONYMOUS;
import static gargoyle.l0x.data.Roles.ROLE_ANONYMOUS;

@Service(AuthImpl.BEAN_ID)
@RequiredArgsConstructor
public class AuthImpl implements Auth {
    public static final String BEAN_ID = "auth";
    private final Users users;
    private final UserConverter userConverter;

    @Override
    public Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public Optional<UserModel> getLoggedUser() {
        return getAuthentication()
                .flatMap(authentication -> (users.find(authentication.getName())
                        .map(userConverter::toModel)));
    }

    @Override
    public Optional<User> getRealUser() {
        return getAuthentication().flatMap(authentication -> (users.find(authentication.getName())));
    }

    @Override
    public boolean isFullyLogged() {
        return getAuthentication().map(authentication ->
                authentication.getAuthorities().stream().noneMatch(grantedAuthority -> {
                    String authority = grantedAuthority.getAuthority();
                    return Objects.equals(ANONYMOUS, authority)
                            || Objects.equals(ROLE_ANONYMOUS, authority);
                }))
                .orElse(false);
    }

    @Override
    public boolean hasRole(String role) {
        return getAuthentication().map(authentication ->
                authentication.getAuthorities().stream().anyMatch(grantedAuthority -> {
                    String authority = grantedAuthority.getAuthority();
                    return Objects.equals(role, authority)
                            || Objects.equals("ROLE_" + role, authority);
                }))
                .orElse(false);
    }
}
