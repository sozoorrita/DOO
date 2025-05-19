package co.edu.uco.FondaControl.data.dao.entity.formapago;

import co.edu.uco.FondaControl.entity.FormaPagoEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;

import java.util.UUID;
import java.util.List;

public interface FormaPagoDAO extends
    CreateDAO<FormaPagoEntity>,
    RetrieveDAO<FormaPagoEntity, UUID>,
    UpdateDAO<FormaPagoEntity, UUID>,
    DeleteDAO<UUID> {

    List<FormaPagoEntity> listByNombre(String nombre);
}
