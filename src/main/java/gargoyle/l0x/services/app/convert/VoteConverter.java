package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.dto.app.VoteDto;
import gargoyle.l0x.entities.app.Vote;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteConverter implements EntityDtoConverter<String, Vote, VoteDto> {

    @Override
    public Vote toEntity(VoteDto dto) {
        return Vote.builder()
                .rate(dto.getRate())
                .creationAlias((dto.getCreation()).getAlias())
                .userUsername((dto.getUser()).getUsername())
                .build();
    }

    @Override
    public VoteDto toDto(Vote entity) {
        return VoteDto.builder()
                .rate(entity.getRate())
                .build();
    }
}
