package gargoyle.l0x.repositories.base;

import gargoyle.l0x.entities.base.IdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface IdEntityRepository<T extends IdEntity<ID>, ID> extends JpaRepository<T, ID> {
    Optional<T> findByUid(String uid);
}
