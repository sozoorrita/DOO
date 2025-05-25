package co.edu.uco.FondaControl.data.dao.entity.mesa;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.entity.MesaEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;

import java.util.UUID;
import java.util.List;

public interface MesaDAO extends
    CreateDAO<MesaEntity>,
    RetrieveDAO<MesaEntity, UUID>,
    UpdateDAO<MesaEntity, UUID>,
    DeleteDAO<UUID> {

    List<MesaEntity> listByEstado(UUID codigoEstadoMesa) throws DataFondaControlException;
}
