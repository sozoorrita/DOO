package co.edu.uco.FondaControl.businesslogic.facade;

import java.util.List;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.VentaDTO;

public interface VentaFacade {
	void registrarVenta(VentaDTO venta) throws FondaControlException;

	void modificarVenta(VentaDTO venta) throws FondaControlException;

	void eliminarVenta(VentaDTO venta) throws FondaControlException;

	List<VentaDTO> consultarVenta(VentaDTO filtro) throws FondaControlException;
}
