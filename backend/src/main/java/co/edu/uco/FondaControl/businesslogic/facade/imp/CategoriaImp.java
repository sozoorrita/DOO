package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.CategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.CategoriaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.dto.CategoriaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.CategoriaFacade;
import co.edu.uco.FondaControl.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaImp implements CategoriaFacade {
    private final CategoriaBusinessLogic logic;

    @Autowired
    public CategoriaImp(DAOFactory dao) {
        this.logic = new CategoriaImpl(dao);
    }

    @Override
    public void registrarCategoria(CategoriaDTO dto) throws FondaControlException {
        CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
        logic.registrarCategoria(domain);
    }

    @Override
    public void modificarCategoria(CategoriaDTO dto) throws FondaControlException {
        CategoriaDomain domain = CategoriaDTOAssembler.getInstancia().toDomain(dto);
        logic.modificarCategoria(domain.getCodigo(), domain);
    }

    @Override
    public void eliminarCategoria(CategoriaDTO dto) throws FondaControlException {
        logic.eliminarCategoria(CategoriaDTOAssembler.getInstancia().toDomain(dto).getCodigo());
    }

    @Override
    public List<CategoriaDTO> consultarCategoria(CategoriaDTO dto) throws FondaControlException {
        CategoriaDomain filter = CategoriaDTOAssembler.getInstancia().toDomain(dto);
        var domains = logic.consultarCategoria(filter);
        return CategoriaDTOAssembler.getInstancia().toDtoList(domains);
    }
}