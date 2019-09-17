package gargoyle.l0x.services.convert;

import gargoyle.l0x.model.base.Model;
import gargoyle.l0x.entities.base.IdEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface EntityModelConverter<ID, E extends IdEntity<ID>, M extends Model<ID>> {
    E toEntity(M model);

    M toModel(E entity);

    default List<E> toEntity(List<M> model) {
        return model.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default List<M> toModel(List<E> entity) {
        return entity.stream().map(this::toModel).collect(Collectors.toList());
    }

    default Set<E> toEntity(Set<M> model) {
        return model.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    default Set<M> toModel(Set<E> entity) {
        return entity.stream().map(this::toModel).collect(Collectors.toSet());
    }

    default Page<E> toEntity(Page<M> model) {
        return model.map(this::toEntity);
    }

    default Page<M> toModel(Page<E> entity) {
        return entity.map(this::toModel);
    }
}
