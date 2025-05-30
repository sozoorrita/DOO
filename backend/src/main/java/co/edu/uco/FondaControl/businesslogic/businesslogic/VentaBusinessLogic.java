package co.edu.uco.FondaControl.businesslogic.businesslogic;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface VentaBusinessLogic {
	void registrarVenta(VentaDomain venta) throws FondaControlException;

	void modificarVenta(UUID codigo, VentaDomain venta) throws FondaControlException;

	void eliminarVenta(UUID codigo) throws FondaControlException;

	void consultarVentaPorCodigo(UUID codigo) throws FondaControlException;

	List<VentaDomain> consultarVenta(VentaDomain filtro) throws FondaControlException;
}
