package gargoyle.l0x.services.users.convert;

import gargoyle.l0x.model.user.UserModel;
import gargoyle.l0x.entities.user.User;
import gargoyle.l0x.services.convert.EntityModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverter implements EntityModelConverter<String, User, UserModel> {
    private final AuthorityConverter authorityConverter;

    @Override
    public User toEntity(UserModel model) {
        return User.builder()
                .username(model.getUsername())
                .email(model.getEmail())
                .authorities(authorityConverter.toEntity(model.getAuthorities()))
                .build();
    }

    @Override
    public UserModel toModel(User entity) {
        return UserModel.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .authorities(authorityConverter.toModel(entity.getAuthorities()))
                .build();
    }
}
