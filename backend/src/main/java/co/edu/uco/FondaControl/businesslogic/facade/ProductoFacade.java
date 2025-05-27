package co.edu.uco.FondaControl.businesslogic.facade;

import java.util.List;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.ProductoDTO;

public interface ProductoFacade {
	void registrarProducto(ProductoDTO producto) throws FondaControlException;

	void modificarProducto(ProductoDTO producto) throws FondaControlException;

	void eliminarProducto(ProductoDTO producto) throws FondaControlException;

	List<ProductoDTO> consultarProducto(ProductoDTO filtro) throws FondaControlException;
}
