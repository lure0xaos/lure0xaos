package gargoyle.l0x.repositories.app;

import gargoyle.l0x.entities.app.Comment;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends IdEntityRepository<Comment, Long> {
    List<Comment> findAllByCreationOrderByDateDesc(Creation creation);
}
