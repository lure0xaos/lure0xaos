package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.dto.app.CreationDto;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreationConverter implements EntityDtoConverter<String, Creation, CreationDto> {
    private final UserConverter userConverter;

    @Override
    public Creation toEntity(CreationDto dto) {
        return Creation.builder()
                .uid(dto.getUid())
                .head(dto.getHead())
                .source(dto.getSource())
                .author(userConverter.toEntity(dto.getAuthor()))
                .genre(dto.getGenre())
                .date(dto.getDate())
                .alias(dto.getAlias())
                .body(dto.getBody())
                .build();
    }

    @Override
    public CreationDto toDto(Creation entity) {
        return CreationDto.builder()
                .uid(entity.getUid())
                .head(entity.getHead())
                .source(entity.getSource())
                .author(userConverter.toDto(entity.getAuthor()))
                .genre(entity.getGenre())
                .date(entity.getDate())
                .alias(entity.getAlias())
                .body(entity.getBody())
                .build();
    }
}
