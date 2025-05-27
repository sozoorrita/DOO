package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.DetalleVenta.DetalleVentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.DetalleVentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.DetalleVentaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.DetalleVentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;

public final class DetalleVentaImp implements DetalleVentaFacade {
	private final DAOFactory daoFactory;
	private final DetalleVentaBusinessLogic businessLogic;

	public DetalleVentaImp() throws FondaControlException {
		try {
			daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
			businessLogic = new DetalleVentaImpl(daoFactory);
		} catch (FondaControlException e) {
			throw e;
		} catch (Exception e) {
			throw BusinessLogicFondaControlException.reportar("No fue posible iniciar el servicio de detalle de venta.",
					"Error al inicializar DetalleVentaImp: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarDetalleVenta(final DetalleVentaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("El detalle de venta no puede ser nulo.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			final DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.registrarDetalleVenta(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error registrando detalle de venta.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void modificarDetalleVenta(final DetalleVentaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("El detalle de venta no puede ser nulo.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			final DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.modificarDetalleVenta(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error modificando detalle de venta.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void eliminarDetalleVenta(final DetalleVentaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("El detalle de venta no puede ser nulo.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			final DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.eliminarDetalleVenta(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error eliminando detalle de venta.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<DetalleVentaDTO> consultarDetalleVenta(final DetalleVentaDTO filtro) throws FondaControlException {
		if (UtilObjeto.esNulo(filtro)) {
			throw BusinessLogicFondaControlException.reportar("El filtro no puede ser nulo.", "filtro es nulo");
		}
		final List<DetalleVentaDomain> lista = businessLogic
				.consultarDetalleVenta(DetalleVentaDTOAssembler.getInstancia().toDomain(filtro));
		return DetalleVentaDTOAssembler.getInstancia().toDTOList(lista);
	}
}
