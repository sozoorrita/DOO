package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.CategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.CategoriaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.dto.CategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.entity.CategoriaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.LayerException;
import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.CategoriaEntity;
import co.edu.uco.FondaControl.businesslogic.facade.CategoriaFacade;
import co.edu.uco.FondaControl.dto.CategoriaDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoriaImp implements CategoriaFacade {
    private final CategoriaBusinessLogic logic;
    private final DAOFactory daoFactory;

    public CategoriaImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.logic = new CategoriaImpl(daoFactory);
    }

    @Override
    public void registrarCategoria(CategoriaDTO dto) throws FondaControlException {
        try {
            CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
            logic.registrarCategoria(domain);
        } catch (FondaControlException ex) {
            throw ex;
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
    public CategoriaDTO consultarCategoriaPorCodigo(CategoriaDTO dto) throws FondaControlException {
        try {
            if (dto == null || dto.getCodigo() == null) {
                throw new FondaControlException(
                        "El código de la categoría a consultar no puede ser nulo.",
                        "Intento de consultar una categoría con código nulo en consultarCategoriaPorCodigo(CategoriaImp)",
                        null,
                        LayerException.BUSINESS_LOGIC
                );
            }

            UUID id = dto.getCodigo();
            CategoriaDomain domain = logic.consultarCategoriaPorCodigo(id);
            return CategoriaDTOAssembler.getInstancia().toDto(domain);

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
            CategoriaEntity filterEntity = CategoriaEntity.builder()
                    .codigo(dto.getCodigo())
                    .nombre(dto.getNombre())
                    .crear();

            CategoriaDAO dao = daoFactory.getCategoriaDAO();
            List<CategoriaEntity> entities = dao.listByFilter(filterEntity);

            List<CategoriaDTO> dtos = new ArrayList<>();
            for (CategoriaEntity ent : entities) {
                CategoriaDTO dtoItem = CategoriaDTOAssembler.getInstancia()
                        .toDto(CategoriaEntityAssembler.getInstancia().toDomain(ent));
                dtoItem.setFechaEliminacion(ent.getFechaEliminacion());
                dtos.add(dtoItem);
            }
            return dtos;
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
