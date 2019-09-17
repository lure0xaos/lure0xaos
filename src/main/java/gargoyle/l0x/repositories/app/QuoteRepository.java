package gargoyle.l0x.repositories.app;

import gargoyle.l0x.entities.app.Quote;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends IdEntityRepository<Quote, String> {
}
