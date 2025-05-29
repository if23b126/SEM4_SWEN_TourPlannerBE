package at.fhtw.tourplannerbe.service.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractMapper<E, D> {

    public abstract D toDto(E entity);
    public abstract E toEntity(D dto);

    public List<D> toDto(Collection<E> entities){
        List<D> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(toDto(entity));
        });

        return dtos;
    }

    public List<E> toEntity(Collection<D> dtos){
        List<E> entities = new ArrayList<>();
        dtos.forEach(dto -> {
            entities.add(toEntity(dto));
        });

        return entities;
    }
}
