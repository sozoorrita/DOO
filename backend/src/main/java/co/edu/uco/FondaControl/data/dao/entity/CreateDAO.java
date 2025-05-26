package co.edu.uco.FondaControl.data.dao.entity;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.entity.ProductoEntity;

public interface CreateDAO<E> {
	void create(E entity) throws DataFondaControlException;

}
