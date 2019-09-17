package gargoyle.l0x.services.users;

import gargoyle.l0x.data.Roles;
import gargoyle.l0x.entities.user.Authority;
import gargoyle.l0x.entities.user.User;
import gargoyle.l0x.repositories.user.AuthorityRepository;
import gargoyle.l0x.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service(UsersImpl.BEAN_ID)
@RequiredArgsConstructor
public class UsersImpl implements Users {
    public static final String BEAN_ID = "users";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final AuthorityRepository authorityRepository;

    @Override
    public Optional<User> find(String username) {
        return repository.findByUsernameOrEmail(username, username);
    }

    @Override
    public User get(String username) {
        return find(username).orElseThrow(() -> new IllegalArgumentException("no user " + username));
    }

    @Override
    public User getIf(String username) {
        return find(username).orElse(null);
    }

    @Override
    public boolean exists(String username) {
        return repository.existsByUsernameOrEmail(username, username);
    }

    @Override
    @Transactional
    public User registerWithAuthorities(String username, CharSequence password, String email, String... authorities) {
        return register(username, password, email, Function.identity(), authorities);
    }

    @Override
    @Transactional
    public User registerWithRoles(String username, CharSequence password, String email, String... authorities) {
        return register(username, password, email, authority ->
                authority.startsWith(Roles.PREFIX_ROLE) ? authority : Roles.PREFIX_ROLE + authority, authorities);
    }

    @Override
    public User register(String username, CharSequence password, String email,
                         Function<? super String, String> function, String[] authorities) {
        User user = User.builder().username(username).password(passwordEncoder.encode(password)).email(email).build();
        Set<Authority> authoritySet = Arrays.stream(authorities).map(function).map(
                authority -> Authority.builder().user(user).authority(authority).build()
        ).collect(Collectors.toSet());
        user.setAuthorities(authoritySet);
        User savedUser = repository.save(user);
        authorityRepository.saveAll(authoritySet);
        return savedUser;
    }
}
