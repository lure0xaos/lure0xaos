package gargoyle.l0x.services.users.convert;

import gargoyle.l0x.model.user.AuthorityModel;
import gargoyle.l0x.entities.user.Authority;
import gargoyle.l0x.services.convert.EntityModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityConverter implements EntityModelConverter<String, Authority, AuthorityModel> {
    @Override
    public Authority toEntity(AuthorityModel model) {
        return Authority.builder()
                .authority(model.getAuthority())
                .build();
    }

    @Override
    public AuthorityModel toModel(Authority entity) {
        return AuthorityModel.builder()
                .authority(entity.getAuthority())
                .build();
    }
}
