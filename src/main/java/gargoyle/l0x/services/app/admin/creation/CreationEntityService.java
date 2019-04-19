package gargoyle.l0x.services.app.admin.creation;

import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.repositories.app.CreationRepository;
import gargoyle.l0x.services.app.admin.base.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreationEntityService extends BaseEntityService<String, Creation, CreationRepository> {
    public CreationEntityService(CreationRepository repository) {
        super(Creation.class, repository);
    }

    @Override
    protected Creation createEntity() {
        return new Creation();
    }

    @Override
    public Sort getDefaultSort() {
        return Sort.by(Sort.Direction.ASC, "alias");
    }

    public List<Creation> findAllByHeadOrSource(String query) {
        return repository.findAllByHeadContainsOrSourceContains(query, query);
    }

    public Page<Creation> findAllByHeadOrSource(String query, int page, int size) {
        return repository.findAllByHeadContainsOrSourceContains(query, query, PageRequest.of(page, size, getDefaultSort()));
    }
}
