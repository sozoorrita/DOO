package co.edu.uco.FondaControl.data.dao.entity.tipoventa;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.TipoVentaEntity;

public interface TipoVentaDAO extends CreateDAO<TipoVentaEntity>, RetrieveDAO<TipoVentaEntity, UUID>,
		UpdateDAO<TipoVentaEntity, UUID>, DeleteDAO<TipoVentaEntity, UUID> {

	List<TipoVentaEntity> listByNombre(String nombre);
}
