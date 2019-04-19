package gargoyle.l0x.services.app.admin.base;

import gargoyle.l0x.dto.base.Dto;
import gargoyle.l0x.entities.base.IdEntity;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import gargoyle.l0x.services.convert.EntityDtoConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public abstract class BaseAdminService<ID, M extends Dto<ID>, E extends IdEntity<ID>, R extends IdEntityRepository<E, ID>> {
    private final BaseEntityService<? super ID, E, R> entityService;
    private final BeanModel<M> dtoModel;
    private final EntityDtoConverter<ID, E, M> converter;

    public BaseAdminService(Class<M> dtoType,
                            EntityDtoConverter<ID, E, M> converter,
                            BaseEntityService<? super ID, E, R> entityService) {
        this.entityService = entityService;
        this.converter = converter;
        dtoModel = BeanModel.of(dtoType);
    }

    public Sort getDefaultSort() {
        return entityService.getDefaultSort();
    }

    public final List<M> findAll(Sort sort) {
        return converter.toDto(entityService.findAll(sort));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public final List<M> findAll(Sort.Direction direction, String... properties) {
        return converter.toDto(entityService.findAll(direction, properties));
    }

    public final Page<M> getPage(Pageable pageable) {
        return converter.toDto(entityService.findAll(pageable));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public final Page<M> getPage(int page, int size, Sort.Direction direction, String... properties) {
        return converter.toDto(entityService.findAll(page, size, direction, properties));
    }

    public final Optional<M> findDtoById(ID id) {
        return entityService.findById(id).map(converter::toDto);
    }

    public final Optional<M> findDtoByUid(String uid) {
        return entityService.findByUid(uid).map(converter::toDto);
    }

    public abstract M createDto();

    protected abstract void updateEntity(E destination, M source);

    public final M saveNew(M dto) {
        E entity = (entityService.createEntity());
        updateEntity(entity, dto);
        return converter.toDto(entityService.save(entity));
    }

    public final M saveById(ID id, M dto) {
        E entity = entityService.findById(id).orElseGet(entityService::createEntity);
        updateEntity(entity, dto);
        return converter.toDto(entityService.save(entity));
    }

    public final M saveByUid(String uid, M dto) {
        E entity = entityService.findByUid(uid).orElseGet(entityService::createEntity);
        updateEntity(entity, dto);
        return converter.toDto(entityService.save(entity));
    }

    public final <T> T field(M dto, String prop) {
        return dtoModel.field(dto, prop);
    }

    public <T> T path(M bean, String path) {
        return dtoModel.path(bean, path);
    }

    public final ID getId(M dto) {
        return dto.getId();
    }

    public final String getUid(M dto) {
        return dto.getUid();
    }

    public final long count() {
        return entityService.count();
    }
}
