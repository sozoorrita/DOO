package co.edu.uco.FondaControl.data.dao.entity;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

import java.util.List;

public interface RetrieveDAO<E, CODIGO> {
    List<E> listByFilter(E entity) throws DataFondaControlException;
    List<E> listAll() throws DataFondaControlException;
    List<E> listByCodigo(CODIGO codigo) throws DataFondaControlException;

    E findById(CODIGO codigo) throws DataFondaControlException;
}
