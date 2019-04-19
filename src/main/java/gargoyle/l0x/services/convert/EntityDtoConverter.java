package gargoyle.l0x.services.convert;

import gargoyle.l0x.dto.base.Dto;
import gargoyle.l0x.entities.base.IdEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface EntityDtoConverter<ID, E extends IdEntity<ID>, M extends Dto<ID>> {
    E toEntity(M dto);

    M toDto(E entity);

    default List<E> toEntity(List<M> dto) {
        return dto.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default List<M> toDto(List<E> entity) {
        return entity.stream().map(this::toDto).collect(Collectors.toList());
    }

    default Set<E> toEntity(Set<M> dto) {
        return dto.stream().map(this::toEntity).collect(Collectors.toSet());
    }

    default Set<M> toDto(Set<E> entity) {
        return entity.stream().map(this::toDto).collect(Collectors.toSet());
    }

    default Page<E> toEntity(Page<M> dto) {
        return dto.map(this::toEntity);
    }

    default Page<M> toDto(Page<E> entity) {
        return entity.map(this::toDto);
    }
}
