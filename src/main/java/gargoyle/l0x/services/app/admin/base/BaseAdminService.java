package gargoyle.l0x.services.app.admin.base;

import gargoyle.l0x.model.base.Model;
import gargoyle.l0x.entities.base.IdEntity;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import gargoyle.l0x.services.convert.EntityModelConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public abstract class BaseAdminService<ID,
        M extends Model<ID>,
        E extends IdEntity<ID>,
        R extends IdEntityRepository<E, ID>> {
    protected final BaseEntityService<? super ID, E, R> entityService;
    private final BeanModel<M> model;
    protected final EntityModelConverter<ID, E, M> converter;

    protected BaseAdminService(Class<M> modelType,
                               EntityModelConverter<ID, E, M> converter,
                               BaseEntityService<? super ID, E, R> entityService) {
        this.entityService = entityService;
        this.converter = converter;
        model = BeanModel.of(modelType);
    }

    public Sort getDefaultSort() {
        return entityService.getDefaultSort();
    }

    public final List<M> findAll(Sort sort) {
        return converter.toModel(entityService.findAll(sort));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public final List<M> findAll(Sort.Direction direction, String... properties) {
        return converter.toModel(entityService.findAll(direction, properties));
    }

    public final Page<M> getPage(Pageable pageable) {
        return converter.toModel(entityService.findAll(pageable));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public final Page<M> getPage(int page, int size, Sort.Direction direction, String... properties) {
        return converter.toModel(entityService.findAll(page, size, direction, properties));
    }

    public final Optional<M> findModelById(ID id) {
        return entityService.findById(id).map(converter::toModel);
    }

    public final Optional<M> findModelByUid(String uid) {
        return entityService.findByUid(uid).map(converter::toModel);
    }

    public abstract M createModel();

    protected abstract void updateEntity(E destination, M source);

    public final M saveNew(M model) {
        E entity = (entityService.createEntity());
        updateEntity(entity, model);
        return converter.toModel(entityService.save(entity));
    }

    public final M saveById(ID id, M model) {
        E entity = entityService.findById(id).orElseGet(entityService::createEntity);
        updateEntity(entity, model);
        return converter.toModel(entityService.save(entity));
    }

    public final M saveByUid(String uid, M model) {
        E entity = entityService.findByUid(uid).orElseGet(entityService::createEntity);
        updateEntity(entity, model);
        return converter.toModel(entityService.save(entity));
    }

    public final <T> T field(M model, String prop) {
        return this.model.field(model, prop);
    }

    public <T> T path(M bean, String path) {
        return model.path(bean, path);
    }

    public final ID getId(M model) {
        return model.getId();
    }

    public final String getUid(M model) {
        return model.getUid();
    }

    public final long count() {
        return entityService.count();
    }
}
