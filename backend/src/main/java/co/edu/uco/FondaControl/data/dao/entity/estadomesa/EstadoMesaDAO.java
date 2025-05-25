package co.edu.uco.FondaControl.data.dao.entity.estadomesa;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.entity.EstadoMesaEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;

import java.util.UUID;
import java.util.List;

public interface EstadoMesaDAO extends
    CreateDAO<EstadoMesaEntity>,
    RetrieveDAO<EstadoMesaEntity, UUID>,
    UpdateDAO<EstadoMesaEntity, UUID>,
    DeleteDAO<UUID> {

    List<EstadoMesaEntity> listByNombre(String nombre) throws DataFondaControlException;
}
