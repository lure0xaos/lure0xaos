package gargoyle.l0x.repositories.app;

import gargoyle.l0x.entities.app.Vote;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends IdEntityRepository<Vote, String> {
    List<Vote> findAllByCreationAlias(String alias);
}
