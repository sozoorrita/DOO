package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class IndicadorInventarioPostgreSQLDAO implements IndicadorInventarioDAO {

    private Connection connection;
    public IndicadorInventarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(IndicadorInventarioEntity entity) {

    }

    @Override
    public List<IndicadorInventarioEntity> listByFilter(IndicadorInventarioEntity entity) {
        return List.of();
    }

    @Override
    public List<IndicadorInventarioEntity> listAll() {
        return List.of();
    }

    @Override
    public List<IndicadorInventarioEntity> listByCodigo(UUID uuid) {
        return List.of();
    }

    @Override
    public void update(UUID uuid, IndicadorInventarioEntity entity) {

    }

    @Override
    public IndicadorInventarioEntity findById(UUID codigo) {
        return null;
    }
}
