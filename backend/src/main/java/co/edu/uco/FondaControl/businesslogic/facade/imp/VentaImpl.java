package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.Venta.VentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.VentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.VentaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.VentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.VentaDTO;

public final class VentaImp implements VentaFacade {
	private final DAOFactory daoFactory;
	private final VentaBusinessLogic businessLogic;

	public VentaImp() throws FondaControlException {
		try {
			daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
			businessLogic = new VentaImpl(daoFactory);
		} catch (FondaControlException e) {
			throw e;
		} catch (Exception e) {
			throw BusinessLogicFondaControlException.reportar("No fue posible iniciar el servicio de ventas.",
					"Error al inicializar VentaImp: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarVenta(final VentaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La venta no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.registrarVenta(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error registrando venta.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void modificarVenta(final VentaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La venta no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.modificarVenta(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error modificando venta.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void eliminarVenta(final VentaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La venta no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.eliminarVenta(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error eliminando venta.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<VentaDTO> consultarVenta(final VentaDTO filtro) throws FondaControlException {
		if (UtilObjeto.esNulo(filtro)) {
			throw BusinessLogicFondaControlException.reportar("El filtro no puede ser nulo.", "filtro es nulo");
		}
		List<VentaDomain> lista = businessLogic.consultarVenta(VentaDTOAssembler.getInstancia().toDomain(filtro));
		return VentaDTOAssembler.getInstancia().toDTOList(lista);
	}
}
