package co.edu.uco.FondaControl.data.dao.entity.producto;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.ProductoEntity;

public interface ProductoDAO extends CreateDAO<ProductoEntity>, RetrieveDAO<ProductoEntity, UUID>,
		UpdateDAO<ProductoEntity, UUID>, DeleteDAO<UUID> {

	List<ProductoEntity> listBySubcategoria(UUID subcategoriaId) throws DataFondaControlException;
}
