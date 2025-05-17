package co.edu.uco.FondaControl.data.dao.entity;

import java.util.List;

public interface RetrieveDAO<E, CODIGO> {

    List<E> listByFilter(E entity);
    List<E> listAll();
    List<E> listByCodigo(CODIGO codigo);

    
}
