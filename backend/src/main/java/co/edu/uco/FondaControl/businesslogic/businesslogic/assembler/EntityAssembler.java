package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler;

import java.util.List;

public interface EntityAssembler<E, D> {

    E toEntity(D domain);
    D toDomain(E entity);
    List<D> toDomainList(List<E> entityList);
}
