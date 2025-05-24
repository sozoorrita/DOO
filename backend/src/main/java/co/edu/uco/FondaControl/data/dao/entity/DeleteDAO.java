package co.edu.uco.FondaControl.data.dao.entity;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

public interface DeleteDAO<CODIGO> {
    void delete(CODIGO codigo) throws DataFondaControlException;
}
