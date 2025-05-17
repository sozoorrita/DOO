package co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo;

import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;


import java.util.UUID;

public interface SesionTrabajoDAO extends
        CreateDAO<SesionTrabajoEntity>,
        UpdateDAO<SesionTrabajoEntity, UUID> {
}