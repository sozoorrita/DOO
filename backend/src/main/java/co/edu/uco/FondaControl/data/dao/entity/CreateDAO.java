package co.edu.uco.FondaControl.data.dao.entity;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

public interface CreateDAO<E> {
    void create(E entity) throws DataFondaControlException;
}
