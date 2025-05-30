package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SubcategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.SubcategoriaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.dto.SubcategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.facade.SubcategoriaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.dto.SubcategoriaDTO;

@Service
public final class SubcategoriaImp implements SubcategoriaFacade {

    private final DAOFactory daoFactory;
    private final SubcategoriaBusinessLogic businessLogic;

    public SubcategoriaImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new SubcategoriaImpl(daoFactory);
    }

    @Override
    public void registrarSubcategoria(SubcategoriaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw BusinessLogicFondaControlException.reportar(
                    "La subcategoría no puede ser nula.",
                    "DTO de subcategoría es nulo"
            );
        }
        try {
            daoFactory.iniciarTransaccion();
            SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
            businessLogic.registrarSubcategoria(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error registrando subcategoría.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarSubcategoria(SubcategoriaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La subcategoría a modificar no puede ser nula y debe contener un código.",
                    "DTO de subcategoría inválido"
            );
        }
        UUID codigo = dto.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            SubcategoriaDomain domain = SubcategoriaDTOAssembler.getInstancia().toDomain(dto);
            businessLogic.modificarSubcategoria(codigo, domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error modificando subcategoría.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarSubcategoria(SubcategoriaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La subcategoría a eliminar no puede ser nula y debe contener un código.",
                    "DTO de subcategoría inválido"
            );
        }
        UUID codigo = dto.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarSubcategoria(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error eliminando subcategoría.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void consultarSubcategoriaPorCodigo(SubcategoriaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La subcategoría a consultar no puede ser nula y debe contener un código.",
                    "DTO de subcategoría inválido"
            );
        }
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.consultarSubcategoriaPorCodigo(dto.getCodigo());
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error consultando subcategoría.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<SubcategoriaDTO> consultarSubcategoria(SubcategoriaDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El filtro de subcategoría no puede ser nulo.",
                    "DTO de filtro inválido"
            );
        }
        try {
            SubcategoriaDomain domainFilter = SubcategoriaDTOAssembler.getInstancia().toDomain(filtro);
            List<SubcategoriaDomain> domains = businessLogic.consultarSubcategoria(domainFilter);
            return SubcategoriaDTOAssembler.getInstancia().toDtoList(domains);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
