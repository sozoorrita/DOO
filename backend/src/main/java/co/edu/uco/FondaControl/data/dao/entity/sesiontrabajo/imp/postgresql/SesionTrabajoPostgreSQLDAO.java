package co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql;

import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;

import java.sql.Connection;
import java.util.UUID;

public class SesionTrabajoPostgreSQLDAO implements co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO {
    private Connection connection;
    public SesionTrabajoPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(co.edu.uco.FondaControl.entity.SesionTrabajoEntity entity) {}

    @Override
    public void update(UUID codigo, SesionTrabajoEntity entity) {

    }

    @Override
    public SesionTrabajoEntity findByUsuario(UUID id) {
        return null;
    }

    @Override
    public SesionTrabajoEntity findById(UUID codigo) {
        return null;
    }

    @Override
    public void update(SesionTrabajoEntity sesionTrabajoEntity, UUID entity) {

    }
}