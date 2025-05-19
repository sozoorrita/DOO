package co.edu.uco.FondaControl.data.dao.entity.venta;

import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.VentaEntity;

public interface VentaDAO extends CreateDAO<VentaEntity>, RetrieveDAO<VentaEntity, UUID>,
		UpdateDAO<VentaEntity, UUID>, DeleteDAO<UUID> {
}
