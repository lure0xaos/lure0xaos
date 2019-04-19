package gargoyle.l0x.services.users.convert;

import gargoyle.l0x.dto.base.Dto;
import gargoyle.l0x.dto.user.UserDto;
import gargoyle.l0x.entities.base.IdEntity;
import gargoyle.l0x.entities.user.User;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConverter<ID, E extends IdEntity<ID>, M extends Dto<ID>> implements EntityDtoConverter<String, User, UserDto> {
    private final AuthorityConverter authorityConverter;

    @SuppressWarnings("unchecked")
    @Override
    public User toEntity(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .authorities(authorityConverter.toEntity(dto.getAuthorities()))
                .build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public UserDto toDto(User entity) {
        return UserDto.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .authorities(authorityConverter.toDto(entity.getAuthorities()))
                .build();
    }
}
