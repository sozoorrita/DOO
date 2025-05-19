package co.edu.uco.FondaControl.data.dao.entity.rol;

import co.edu.uco.FondaControl.entity.RolEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;

import java.util.UUID;
import java.util.List;

public interface RolDAO extends
    CreateDAO<RolEntity>,
    RetrieveDAO<RolEntity, UUID>,
    UpdateDAO<RolEntity, UUID>,
    DeleteDAO<UUID> {

    List<RolEntity> listByNombre(String nombre);
}
