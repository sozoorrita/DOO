package co.edu.uco.FondaControl.businesslogic.facade;

import java.util.List;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;

public interface DetalleVentaFacade {
	void registrarDetalleVenta(DetalleVentaDTO detalleVenta) throws FondaControlException;

	void modificarDetalleVenta(DetalleVentaDTO detalleVenta) throws FondaControlException;

	void eliminarDetalleVenta(DetalleVentaDTO detalleVenta) throws FondaControlException;

	List<DetalleVentaDTO> consultarDetalleVenta(DetalleVentaDTO filtro) throws FondaControlException;
}
