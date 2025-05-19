package co.edu.uco.FondaControl.data.dao.entity.detalleventa;

import java.util.UUID;

import co.edu.uco.FondaControl.data.dao.entity.CreateDAO;
import co.edu.uco.FondaControl.data.dao.entity.DeleteDAO;
import co.edu.uco.FondaControl.data.dao.entity.RetrieveDAO;
import co.edu.uco.FondaControl.data.dao.entity.UpdateDAO;
import co.edu.uco.FondaControl.entity.DetalleVentaEntity;

public interface DetalleVentaDAO extends CreateDAO<DetalleVentaEntity>, RetrieveDAO<DetalleVentaEntity, UUID>,
		UpdateDAO<DetalleVentaEntity, UUID>, DeleteDAO<UUID> {
}
