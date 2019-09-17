package gargoyle.l0x.services.app.convert;

import gargoyle.l0x.model.app.CommentModel;
import gargoyle.l0x.entities.app.Comment;
import gargoyle.l0x.services.convert.EntityModelConverter;
import gargoyle.l0x.services.users.convert.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentConverter implements EntityModelConverter<Long, Comment, CommentModel> {
    private final UserConverter userConverter;

    @Override
    public Comment toEntity(CommentModel model) {
        return Comment.builder()
                .uid(model.getUid())
                .head(model.getHead())
                .source(model.getSource())
                .author(userConverter.toEntity(model.getAuthor()))
                .date(model.getDate())
                .body(model.getBody())
                .build();
    }

    @Override
    public CommentModel toModel(Comment entity) {
        return CommentModel.builder()
                .uid(entity.getUid())
                .head(entity.getHead())
                .source(entity.getSource())
                .author(userConverter.toModel(entity.getAuthor()))
                .date(entity.getDate())
                .body(entity.getBody())
                .build();
    }
}
