package co.edu.uco.FondaControl.businesslogic.businesslogic;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface ProductoBusinessLogic {
	void registrarProducto(ProductoDomain producto) throws FondaControlException;

	void modificarProducto(UUID codigo, ProductoDomain producto) throws FondaControlException;

	void eliminarProducto(UUID codigo) throws FondaControlException;

	ProductoDomain consultarProductoPorCodigo(UUID codigo) throws FondaControlException;

	List<ProductoDomain> consultarProducto(ProductoDomain filtro) throws FondaControlException;
}
