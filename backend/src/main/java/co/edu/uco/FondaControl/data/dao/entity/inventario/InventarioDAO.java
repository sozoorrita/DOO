package co.edu.uco.FondaControl.data.dao.entity.inventario;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.InventarioEntity;

import java.util.UUID;

public interface InventarioDAO extends
        CreateDAO<InventarioEntity>,
        RetrieveDAO<InventarioEntity, UUID>,
        UpdateDAO<InventarioEntity, UUID> {
    void createOrUpdate(InventarioEntity entity) throws DataFondaControlException;

    void delete(UUID codigo) throws DataFondaControlException;
}