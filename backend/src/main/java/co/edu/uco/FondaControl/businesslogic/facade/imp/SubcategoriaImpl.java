package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.Subcategoria.SubcategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.SubcategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.SubcategoriaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.SubcategoriaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.SubcategoriaDTO;

public final class SubcategoriaImp implements SubcategoriaFacade {
	private final DAOFactory daoFactory;
	private final SubcategoriaBusinessLogic businessLogic;

	public SubcategoriaImp() throws FondaControlException {
		try {
			daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
			businessLogic = new SubcategoriaImpl(daoFactory);
		} catch (FondaControlException e) {
			throw e;
		} catch (Exception e) {
			throw BusinessLogicFondaControlException.reportar("No fue posible iniciar el servicio de subcategorías.",
					"Error al inicializar SubcategoriaImp: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarSubcategoria(final SubcategoriaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La subcategoría no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.registrarSubcategoria(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error registrando subcategoría.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void modificarSubcategoria(final SubcategoriaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La subcategoría no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.modificarSubcategoria(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error modificando subcategoría.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void eliminarSubcategoria(final SubcategoriaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La subcategoría no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.eliminarSubcategoria(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error eliminando subcategoría.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<SubcategoriaDTO> consultarSubcategoria(final SubcategoriaDTO filtro) throws FondaControlException {
		if (UtilObjeto.esNulo(filtro)) {
			throw BusinessLogicFondaControlException.reportar("El filtro no puede ser nulo.", "filtro es nulo");
		}
		List<SubcategoriaDomain> lista = businessLogic
				.consultarSubcategoria(SubcategoriaDTOAssembler.getInstancia().toDomain(filtro));
		return SubcategoriaDTOAssembler.getInstancia().toDTOList(lista);
	}
}
