package co.edu.uco.FondaControl.data.dao.entity.tipoventa;

import co.edu.uco.FondaControl.entity.TipoVentaEntity;
import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;

import java.util.UUID;
import java.util.List;

public interface TipoVentaDAO extends
    CreateDAO<TipoVentaEntity>,
    RetrieveDAO<TipoVentaEntity, UUID>,
    UpdateDAO<TipoVentaEntity, UUID>,
    DeleteDAO<TipoVentaEntity, UUID> {

    List<TipoVentaEntity> listByNombre(String nombre);
}
