package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.CategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.dto.CategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.businesslogic.facade.CategoriaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.CategoriaDTO;

public final class CategoriaImp implements CategoriaFacade {

	private final DAOFactory daoFactory;
	private final CategoriaBusinessLogic businessLogic;

	public CategoriaImp() throws FondaControlException {
		try {
			daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
			businessLogic = new co.edu.uco.FondaControl.businesslogic.businesslogic.impl.CategoriaImpl(daoFactory);
		} catch (FondaControlException e) {
			throw e;
		} catch (Exception e) {
			throw BusinessLogicFondaControlException.reportar("No fue posible iniciar el servicio de categorías.",
					"Error al inicializar CategoriaImp: " + e.getMessage(), e);
		}
	}

	@Override
	public void registrarCategoria(final CategoriaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La categoría no puede ser nula.", "dto es nulo");
		}
		try {
			daoFactory.iniciarTransaccion();
			final CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.registrarCategoria(domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error registrando categoría.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void modificarCategoria(final CategoriaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La categoría no puede ser nula.", "dto es nulo");
		}
		final UUID codigo = dto.getCodigo();
		try {
			daoFactory.iniciarTransaccion();
			final CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
			businessLogic.modificarCategoria(codigo, domain);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error modificando categoría.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public void eliminarCategoria(final CategoriaDTO dto) throws FondaControlException {
		if (UtilObjeto.esNulo(dto)) {
			throw BusinessLogicFondaControlException.reportar("La categoría no puede ser nula.", "dto es nulo");
		}
		final UUID codigo = dto.getCodigo();
		try {
			daoFactory.iniciarTransaccion();
			businessLogic.eliminarCategoria(codigo);
			daoFactory.confirmarTransaccion();
		} catch (FondaControlException e) {
			daoFactory.cancelarTransaccion();
			throw e;
		} catch (Exception e) {
			daoFactory.cancelarTransaccion();
			throw BusinessLogicFondaControlException.reportar("Error eliminando categoría.", e.getMessage(), e);
		} finally {
			daoFactory.cerrarConexion();
		}
	}

	@Override
	public List<CategoriaDTO> consultarCategoria(final CategoriaDTO filtro) throws FondaControlException {
		if (UtilObjeto.esNulo(filtro)) {
			throw BusinessLogicFondaControlException.reportar("El filtro no puede ser nulo.", "filtro es nulo");
		}
		final List<CategoriaDomain> lista = businessLogic.consultarCategoria(
				CategoriaDTOAssembler.getInstancia().toDomain(filtro));
		return CategoriaDTOAssembler.getInstancia().toDtoList(lista);
	}
}
