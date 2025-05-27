package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler;

import java.util.List;

public interface DTOAssembler <T, D > {

    T toDto(D domain);

    D toDomain(T dto);

    List<D> toDomainList(List<T> dtoList);

    List<T> toDtoList(List<D> domainList);

}
