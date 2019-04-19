package gargoyle.l0x.repositories.app;

import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreationRepository extends IdEntityRepository<Creation, String> {
    List<Creation> findAllByHeadContainsOrSourceContains(String headQuery, String sourceQuery);

    Page<Creation> findAllByHeadContainsOrSourceContains(String headQuery, String sourceQuery, Pageable pageable);
}
