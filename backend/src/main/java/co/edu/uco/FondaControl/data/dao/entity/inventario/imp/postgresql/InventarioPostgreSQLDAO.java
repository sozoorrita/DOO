package co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.entity.InventarioEntity;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class InventarioPostgreSQLDAO implements InventarioDAO {
    private Connection connection;
    public InventarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(InventarioEntity entity) {

    }

    @Override
    public List<InventarioEntity> listByFilter(InventarioEntity entity) {
        return List.of();
    }

    @Override
    public List<InventarioEntity> listAll() {
        return List.of();
    }

    @Override
    public List<InventarioEntity> listByCodigo(UUID uuid) {
        return List.of();
    }

    @Override
    public void update(UUID uuid, InventarioEntity entity) {

    }
}
