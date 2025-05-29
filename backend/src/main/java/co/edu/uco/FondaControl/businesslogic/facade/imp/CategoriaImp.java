package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.CategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.CategoriaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.dto.CategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.LayerException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.CategoriaFacade;
import co.edu.uco.FondaControl.dto.CategoriaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoriaImp implements CategoriaFacade {
    private final CategoriaBusinessLogic logic;

    public CategoriaImp(DAOFactory daoFactory) {
        this.logic = new CategoriaImpl(daoFactory);
    }

    @Override
    public void registrarCategoria(CategoriaDTO dto) throws FondaControlException {
        try {
            CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
            logic.registrarCategoria(domain);
        } catch (FondaControlException ex) {
            throw ex; // Ya está estructurada
        } catch (Exception ex) {
            throw new FondaControlException(
                    "No fue posible registrar la categoría, por favor intente de nuevo o contacte al administrador.",
                    "Error técnico en registrarCategoria(CategoriaImp): " + ex.getMessage(),
                    ex,
                    LayerException.BUSINESS_LOGIC
            );
        }
    }

    @Override
    public void modificarCategoria(CategoriaDTO dto) throws FondaControlException {
        try {
            CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
            logic.modificarCategoria(domain.getCodigo(), domain);
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new FondaControlException(
                    "No fue posible modificar la categoría, por favor intente de nuevo o contacte al administrador.",
                    "Error técnico en modificarCategoria(CategoriaImp): " + ex.getMessage(),
                    ex,
                    LayerException.BUSINESS_LOGIC
            );
        }
    }

    @Override
    public void eliminarCategoria(CategoriaDTO dto) throws FondaControlException {
        try {
            CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
            logic.eliminarCategoria(domain.getCodigo());
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new FondaControlException(
                    "No fue posible eliminar la categoría, por favor intente de nuevo o contacte al administrador.",
                    "Error técnico en eliminarCategoria(CategoriaImp): " + ex.getMessage(),
                    ex,
                    LayerException.BUSINESS_LOGIC
            );
        }
    }

    @Override
    public void consultarCategoriaPorCodigo(CategoriaDTO categoria) throws FondaControlException {
        try {
            if (categoria == null || categoria.getCodigo() == null) {
                throw new FondaControlException(
                        "El código de la categoría a consultar no puede ser nulo.",
                        "Intento de consultar una categoría con código nulo en consultarCategoriaPorCodigo(CategoriaImp)",
                        null,
                        LayerException.BUSINESS_LOGIC
                );
            }
            logic.consultarCategoriaPorCodigo(categoria.getCodigo());
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new FondaControlException(
                    "No fue posible consultar la categoría, por favor intente de nuevo o contacte al administrador.",
                    "Error técnico en consultarCategoriaPorCodigo(CategoriaImp): " + ex.getMessage(),
                    ex,
                    LayerException.BUSINESS_LOGIC
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> consultarCategoria(CategoriaDTO dto) throws FondaControlException {
        try {
            CategoriaDomain filter = CategoriaDTOAssembler.getInstancia().toDomain(dto);
            var domains = logic.consultarCategoria(filter);
            return CategoriaDTOAssembler.getInstancia().toDtoList(domains);
        } catch (FondaControlException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new FondaControlException(
                    "No fue posible consultar las categorías, por favor intente de nuevo o contacte al administrador.",
                    "Error técnico en consultarCategoria(CategoriaImp): " + ex.getMessage(),
                    ex,
                    LayerException.BUSINESS_LOGIC
            );
        }
    }
}
