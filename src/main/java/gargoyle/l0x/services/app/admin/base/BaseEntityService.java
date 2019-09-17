package gargoyle.l0x.services.app.admin.base;

import gargoyle.l0x.entities.base.IdEntity;
import gargoyle.l0x.repositories.base.IdEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class BaseEntityService<ID, E extends IdEntity<ID>, R extends IdEntityRepository<E, ID>> {
    protected final Class<E> entityType;
    protected final R repository;

    protected BaseEntityService(Class<E> entityType, R repository) {
        this.entityType = entityType;
        this.repository = repository;
    }

    protected final List<E> getAllSorted() {
        Sort defaultSort = getDefaultSort();
        return null == defaultSort ? repository.findAll() : findAll(defaultSort);
    }

    @SuppressWarnings("ReturnOfNull")
    public Sort getDefaultSort() {
        return null;
    }

    protected final List<E> findAll(Sort sort) {
        return repository.findAll(null == sort ? getDefaultSort() : sort);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    protected final List<E> findAll(String direction, String... properties) {
        return repository.findAll(Sort.by(Sort.Direction.valueOf(direction.toUpperCase(Locale.ENGLISH)), properties));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    protected final List<E> findAll(Sort.Direction direction, String... properties) {
        return repository.findAll(Sort.by(direction, properties));
    }

    protected final Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    protected final Page<E> findAll(int page, int size, Sort.Direction direction, String... properties) {
        return repository.findAll(PageRequest.of(page, size, direction, properties));
    }

    public long count() {
        return repository.count();
    }

    protected final Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    protected final Optional<E> findByUid(String uid) {
        return repository.findByUid(uid);
    }

    protected final E get(ID id) {
        return repository.findById(id).orElseThrow(() -> noElement(id));
    }

    protected final E getIf(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    protected E save(E entity) {
        return repository.saveAndFlush(entity);
    }

    @Transactional
    protected void delete(ID id) {
        repository.deleteById(id);
    }

    @SuppressWarnings("MethodMayBeStatic")
    @Transactional
    protected <RES> RES invoke(Supplier<RES> operation) {
        return operation.get();
    }

    @Transactional
    protected Optional<E> transform(Function<? super E, ? extends E> operation, ID id) {
        return repository.findById(id).map(operation).map(repository::saveAndFlush);
    }

    @Transactional
    protected Optional<E> transform(Predicate<? super E> validation, Function<? super E, ? extends E> operation,
                                    ID id) {
        return repository.findById(id).filter(validation).map(operation).map(repository::saveAndFlush);
    }

    protected final ID getId(E entity) {
        return entity.getId();
    }

    protected final String getUid(E entity) {
        return entity.getUid();
    }

    protected abstract E createEntity();

    private NoSuchElementException noElement(ID id) {
        return new NoSuchElementException(entityType + " " + id + " not found");
    }
}
