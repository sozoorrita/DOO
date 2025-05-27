package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SubcategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.dto.SubcategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
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
            businessLogic = new co.edu.uco.FondaControl.businesslogic.businesslogic.impl.SubcategoriaImpl(daoFactory);
        } catch (FondaControlException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No fue posible iniciar el servicio de subcategorías.",
                    "Error al inicializar SubcategoriaImp: " + e.getMessage(), e
            );
        }
    }

    @Override
    public void registrarSubcategoria(final SubcategoriaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw BusinessLogicFondaControlException.reportar("La subcategoría no puede ser nula.", "dto es nulo");
        }
        try {
            daoFactory.iniciarTransaccion();
            final SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
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
        final UUID codigo = dto.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            final SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
            businessLogic.modificarSubcategoria(codigo, domain);
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
        final UUID codigo = dto.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarSubcategoria(codigo);
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
            throw BusinessLogicFondaControlException.reportar("El filtro de subcategoría no puede ser nulo.", "filtro es nulo");
        }
        final List<SubcategoriaDomain> lista = businessLogic.consultarSubcategoria(
                SubcategoriaDTOAssembler.getInstancia().toDomain(filtro));
        return SubcategoriaDTOAssembler.getInstancia().toDtoList(lista);
    }
}
