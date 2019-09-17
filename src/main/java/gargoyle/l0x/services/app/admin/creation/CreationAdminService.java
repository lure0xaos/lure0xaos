package gargoyle.l0x.services.app.admin.creation;

import gargoyle.l0x.model.app.CreationModel;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.err.NotLoggedInException;
import gargoyle.l0x.repositories.app.CreationRepository;
import gargoyle.l0x.services.app.admin.base.BaseAdminService;
import gargoyle.l0x.services.app.convert.CreationConverter;
import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.services.away.Away;
import gargoyle.l0x.services.md.MD;
import org.springframework.stereotype.Service;

import java.util.List;

import static gargoyle.l0x.services.app.admin.base.BaseAdminServiceUtil.*;

@Service
public class CreationAdminService extends BaseAdminService<String, CreationModel, Creation, CreationRepository> {
    private final Auth auth;
    private final Away away;
    private final MD md;
    private final CreationRepository repository;

    public CreationAdminService(Auth auth, Away away, MD md,
                                CreationConverter converter, CreationEntityService entityService,
                                CreationRepository repository) {
        super(CreationModel.class, converter, entityService);
        this.auth = auth;
        this.away = away;
        this.md = md;
        this.repository = repository;
    }

    @Override
    public CreationModel createModel() {
        return CreationModel.builder()
                .author(auth.getLoggedUser().orElseThrow(NotLoggedInException::new))
                .build();
    }

    @Override
    public void updateEntity(Creation destination, CreationModel source) {
        updateSourceEntity(destination, source, text -> away.rewriteText(md.toHtml(text)));
        updateOwnerEntity(destination, source, () -> auth.getRealUser().orElseThrow(NotLoggedInException::new));
        updateDatedEntity(destination, source);
        updateAliasedEntity(destination, source);
        destination.setGenre(source.getGenre());
    }

    public List<String> getGenres() {
        return repository.getGenres();
    }
}
