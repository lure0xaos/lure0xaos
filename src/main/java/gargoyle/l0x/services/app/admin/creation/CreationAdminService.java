package gargoyle.l0x.services.app.admin.creation;

import gargoyle.l0x.dto.app.CreationDto;
import gargoyle.l0x.entities.app.Creation;
import gargoyle.l0x.repositories.app.CreationRepository;
import gargoyle.l0x.services.app.admin.base.BaseAdminService;
import gargoyle.l0x.services.app.convert.CreationConverter;
import gargoyle.l0x.services.auth.Auth;
import gargoyle.l0x.services.md.MD;
import org.springframework.stereotype.Service;

import static gargoyle.l0x.services.app.admin.base.BaseAdminServiceUtil.*;

@Service
public class CreationAdminService extends BaseAdminService<String, CreationDto, Creation, CreationRepository> {
    private final Auth auth;
    private final MD md;

    public CreationAdminService(CreationRepository repository, Auth auth, MD md,
                                CreationConverter converter, CreationEntityService entityService) {
        super(CreationDto.class, converter, entityService);
        this.auth = auth;
        this.md = md;
    }

    @Override
    public CreationDto createDto() {
        return CreationDto.builder()
                .author(auth.getLoggedUser().orElseThrow())
                .build();
    }

    @Override
    public void updateEntity(Creation destination, CreationDto source) {
        updateSourceEntity(destination, source, md::toHtml);
        updateOwnerEntity(destination, source, () -> auth.getRealUser().orElseThrow());
        updateDatedEntity(destination, source);
        updateAliasedEntity(destination, source);
        destination.setGenre(source.getGenre());
    }
}
