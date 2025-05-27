package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SubcategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.entity.SubcategoriaEntityAssembler;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.entity.subcategoria.SubcategoriaDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.SubcategoriaEntity;

public final class SubcategoriaImpl implements SubcategoriaBusinessLogic {

    private final DAOFactory daoFactory;

    public SubcategoriaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarSubcategoria(final SubcategoriaDomain domain) throws BusinessLogicFondaControlException, DataFondaControlException {
        if (UtilObjeto.esNulo(domain)) {
            throw BusinessLogicFondaControlException.reportar("No se puede registrar una subcategoría nula.");
        }

        if (UtilTexto.getInstancia().esNula(domain.getNombre()) ||
                !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(domain.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la subcategoría es inválido.");
        }

        if (UtilUUID.esValorDefecto(domain.getCodigoCategoria())) {
            throw BusinessLogicFondaControlException.reportar("Debe especificar una categoría válida para la subcategoría.");
        }

        final SubcategoriaEntity entity = SubcategoriaEntityAssembler.getInstancia().toEntity(domain);
        daoFactory.getSubcategoriaDAO().create(entity);
    }

    @Override
    public void modificarSubcategoria(final UUID codigo, final SubcategoriaDomain domain) throws BusinessLogicFondaControlException, DataFondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la subcategoría a modificar es inválido.");
        }

        if (UtilObjeto.esNulo(domain)) {
            throw BusinessLogicFondaControlException.reportar("La subcategoría a modificar no puede ser nula.");
        }

        if (UtilTexto.getInstancia().esNula(domain.getNombre()) ||
                !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(domain.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la subcategoría es inválido.");
        }

        if (UtilUUID.esValorDefecto(domain.getCodigoCategoria())) {
            throw BusinessLogicFondaControlException.reportar("Se debe especificar una categoría válida.");
        }

        domain.setCodigo(codigo);
        final SubcategoriaEntity entity = SubcategoriaEntityAssembler.getInstancia().toEntity(domain);
        daoFactory.getSubcategoriaDAO().update(codigo, entity);
    }

    @Override
    public void eliminarSubcategoria(final UUID codigo) throws BusinessLogicFondaControlException, DataFondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la subcategoría a eliminar es inválido.");
        }

        daoFactory.getSubcategoriaDAO().delete(codigo);
    }

    @Override
    public List<SubcategoriaDomain> consultarSubcategoria(final SubcategoriaDomain filtro) throws DataFondaControlException {
        final SubcategoriaEntity entityFiltro = SubcategoriaEntityAssembler.getInstancia().toEntity(filtro);
        final List<SubcategoriaEntity> resultados = daoFactory.getSubcategoriaDAO().listByFilter(entityFiltro);
        return SubcategoriaEntityAssembler.getInstancia().toDomainList(resultados);
    }
}
