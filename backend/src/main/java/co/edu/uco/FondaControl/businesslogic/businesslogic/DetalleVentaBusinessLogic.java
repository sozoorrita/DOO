package co.edu.uco.FondaControl.businesslogic.businesslogic;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface DetalleVentaBusinessLogic {
	void registrarDetalleVenta(DetalleVentaDomain detalleVenta) throws FondaControlException;

	void modificarDetalleVenta(UUID codigo, DetalleVentaDomain detalleVenta) throws FondaControlException;

	void eliminarDetalleVenta(UUID codigo) throws FondaControlException;

	List<DetalleVentaDomain> consultarDetalleVenta(DetalleVentaDomain filtro) throws FondaControlException;
}
