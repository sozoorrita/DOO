package co.edu.uco.FondaControl.data.dao.entity.inventario;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.InventarioEntity;

import java.util.UUID;

public interface InventarioDAO extends
        CreateDAO<InventarioEntity>,
        RetrieveDAO<InventarioEntity, UUID>,
        UpdateDAO<InventarioEntity, UUID> {
    void update(UUID uuid, InventarioEntity entity);

    InventarioEntity findById(UUID codigo);

    void createOrUpdate(InventarioEntity entity);
}

