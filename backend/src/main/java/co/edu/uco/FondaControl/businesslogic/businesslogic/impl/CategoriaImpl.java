package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.CategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.entity.CategoriaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.CategoriaEntity;

public final class CategoriaImpl implements CategoriaBusinessLogic {

    private final DAOFactory daoFactory;

    public CategoriaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarCategoria(final CategoriaDomain categoria) throws FondaControlException {
        if (UtilObjeto.esNulo(categoria)) {
            throw BusinessLogicFondaControlException.reportar("No es posible registrar una categoría nula.");
        }

        if (UtilTexto.getInstancia().esNula(categoria.getNombre()) ||
                !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(categoria.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la categoría es inválido.");
        }

        final CategoriaEntity entity = CategoriaEntityAssembler.getInstancia().toEntity(categoria);
        daoFactory.getCategoriaDAO().create(entity);
    }

    @Override
    public void consultarCategoriaPorCodigo(UUID codigo) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la categoría a consultar es inválido.");
        }

        CategoriaEntity entity = daoFactory.getCategoriaDAO().findById(codigo);
        if (UtilObjeto.esNulo(entity) || UtilUUID.esValorDefecto(entity.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar("No existe una categoría con el código proporcionado.");
        }


    }


    @Override
    public void modificarCategoria(final UUID codigo, final CategoriaDomain categoria) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la categoría a modificar es inválido.");
        }

        if (UtilObjeto.esNulo(categoria) ||
                UtilTexto.getInstancia().esNula(categoria.getNombre()) ||
                !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(categoria.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("La categoría a modificar es inválida.");
        }

        categoria.setCodigo(codigo);
        final CategoriaEntity entity = CategoriaEntityAssembler.getInstancia().toEntity(categoria);
        daoFactory.getCategoriaDAO().update(codigo, entity);
    }

    @Override
    public void eliminarCategoria(final UUID codigo) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la categoría a eliminar es inválido.");
        }

        daoFactory.getCategoriaDAO().delete(codigo);
    }

    @Override
    public List<CategoriaDomain> consultarCategoria(final CategoriaDomain filtro) throws FondaControlException {
        final CategoriaEntity entityFiltro = CategoriaEntityAssembler.getInstancia().toEntity(filtro);
        final List<CategoriaEntity> resultados = daoFactory.getCategoriaDAO().listByFilter(entityFiltro);
        return CategoriaEntityAssembler.getInstancia().toDomainList(resultados);
    }
}
