package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.Producto.ProductoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.ProductoDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.ProductoImpl;
import co.edu.uco.FondaControl.businesslogic.facade.ProductoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.ProductoDTO;

public final class ProductoImp implements ProductoFacade {
	private final DAOFactory daoFactory;
	private final ProductoBusinessLogic businessLogic;

	public ProductoImp() throws FondaControlException {
		try {
			daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
			businessLogic = new ProductoImpl(daoFactory);
		} catch (FondaControlException e) {
			throw e;
		} catch (Exception e) {
			throw BusinessLogicFondaControlException.reportar("No fue posible iniciar el servicio de productos.",
					"Error al inicializar ProductoImp: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarProducto(final ProductoDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("El producto no puede ser nulo.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			ProductoDomain domain = ProductoDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.registrarProducto(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error registrando producto.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void modificarProducto(final ProductoDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("El producto no puede ser nulo.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			ProductoDomain domain = ProductoDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.modificarProducto(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error modificando producto.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void eliminarProducto(final ProductoDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("El producto no puede ser nulo.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			ProductoDomain domain = ProductoDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.eliminarProducto(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error eliminando producto.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<ProductoDTO> consultarProducto(final ProductoDTO filtro) throws FondaControlException {
		if (UtilObjeto.esNulo(filtro)) {
			throw BusinessLogicFondaControlException.reportar("El filtro no puede ser nulo.", "filtro es nulo");
		}
		List<ProductoDomain> lista = businessLogic
				.consultarProducto(ProductoDTOAssembler.getInstancia().toDomain(filtro));
		return ProductoDTOAssembler.getInstancia().toDTOList(lista);
	}
}
