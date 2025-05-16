package co.edu.uco.FondaControl.data.dao.entity.inventario;

import co.edu.uco.FondaControl.entity.InventarioEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;


import java.util.List;
import java.util.UUID;

public interface InventarioDAO extends
        CreateDAO<InventarioEntity>,
        RetrieveDAO<InventarioEntity, UUID>,
        UpdateDAO<InventarioEntity, UUID> {
    void create(InventarioEntity entity);

    List<InventarioEntity> listByFilter(InventarioEntity entity);

    List<InventarioEntity> listAll();

    List<InventarioEntity> listByCodigo(UUID uuid);

    void update(UUID uuid, InventarioEntity entity);
}