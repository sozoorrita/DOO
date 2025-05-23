package co.edu.uco.FondaControl.data.dao.entity;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

public interface UpdateDAO<E, CODIGO> {
    void update(CODIGO codigo, E entity) throws DataFondaControlException;
}
