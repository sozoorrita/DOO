package co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.sql.Connection;
import java.util.UUID;

public class UsuarioPostgreSQLDAO implements UsuarioDAO {
    private Connection connection;
    public UsuarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(UsuarioEntity entity) {

    }

    @Override
    public void delete(UsuarioEntity usuarioEntity) {

    }

    @Override
    public void update(UUID uuid, UsuarioEntity entity) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void update(UsuarioEntity usuarioEntity, UUID entity) {

    }
}
