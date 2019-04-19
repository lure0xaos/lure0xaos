package gargoyle.l0x.services.users;

import gargoyle.l0x.entities.user.User;

import java.util.Optional;
import java.util.function.Function;

public interface Users {
    Optional<User> find(String username);

    User get(String username);

    User getIf(String username);

    boolean exists(String username);

    User registerWithAuthorities(String username, CharSequence password, String email, String... authorities);

    User registerWithRoles(String username, CharSequence password, String email, String... authorities);

    User register(String username, CharSequence password, String email, Function<? super String, String> function, String[] authorities);
}
