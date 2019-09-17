package gargoyle.l0x.repositories.app;

import gargoyle.l0x.entities.app.News;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NewsRepository extends IdEntityRepository<News, LocalDateTime> {
}
