package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.model.app.VoteModel;
import gargoyle.l0x.entities.app.Vote;
import gargoyle.l0x.services.convert.EntityModelConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteConverter implements EntityModelConverter<String, Vote, VoteModel> {

    @Override
    public Vote toEntity(VoteModel model) {
        return Vote.builder()
                .rate(model.getRate())
                .creationAlias((model.getCreation()).getAlias())
                .userUsername((model.getUser()).getUsername())
                .build();
    }

    @Override
    public VoteModel toModel(Vote entity) {
        return VoteModel.builder()
                .rate(entity.getRate())
                .build();
    }
}
