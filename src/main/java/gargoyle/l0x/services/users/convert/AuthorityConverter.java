package gargoyle.l0x.services.users.convert;

import gargoyle.l0x.dto.base.Dto;
import gargoyle.l0x.dto.user.AuthorityDto;
import gargoyle.l0x.entities.base.IdEntity;
import gargoyle.l0x.entities.user.Authority;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityConverter<ID, E extends IdEntity<ID>, M extends Dto<ID>> implements EntityDtoConverter<String, Authority, AuthorityDto> {
    @Override
    public Authority toEntity(AuthorityDto dto) {
        return Authority.builder()
                .authority(dto.getAuthority())
                .build();
    }

    @Override
    public AuthorityDto toDto(Authority entity) {
        return AuthorityDto.builder()
                .authority(entity.getAuthority())
                .build();
    }
}
