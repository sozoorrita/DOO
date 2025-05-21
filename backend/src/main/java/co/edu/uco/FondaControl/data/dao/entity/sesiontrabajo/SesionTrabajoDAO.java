package co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;

import java.util.UUID;

public interface SesionTrabajoDAO extends
        CreateDAO<SesionTrabajoEntity>,
        UpdateDAO<SesionTrabajoEntity, UUID> {

    void update(UUID codigo, SesionTrabajoEntity entity);

    SesionTrabajoEntity findById(UUID codigo);

    SesionTrabajoEntity findByUsuario(UUID idUsuario);
}