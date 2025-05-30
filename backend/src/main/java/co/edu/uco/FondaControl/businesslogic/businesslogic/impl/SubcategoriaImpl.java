package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SubcategoriaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.entity.SubcategoriaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.SubcategoriaEntity;

public final class SubcategoriaImpl implements SubcategoriaBusinessLogic {

    private final DAOFactory daoFactory;

    public SubcategoriaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarSubcategoria(final SubcategoriaDomain subcategoria) throws FondaControlException {
        validarSubcategoria(subcategoria);

        final var entity = SubcategoriaEntityAssembler.getInstancia().toEntity(subcategoria);
        daoFactory.getSubcategoriaDAO().create(entity);
    }

    @Override
    public void modificarSubcategoria(final UUID codigo, final SubcategoriaDomain subcategoria) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la subcategoría no puede ser el valor por defecto.");
        }
        validarSubcategoria(subcategoria);

        subcategoria.setCodigo(codigo);
        final var entity = SubcategoriaEntityAssembler.getInstancia().toEntity(subcategoria);
        daoFactory.getSubcategoriaDAO().update(codigo, entity);
    }

    @Override
    public void eliminarSubcategoria(final UUID codigo) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la subcategoría a eliminar es inválido.");
        }

        daoFactory.getSubcategoriaDAO().delete(codigo);
    }

    @Override
    public void consultarSubcategoriaPorCodigo(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código de la subcategoría no puede ser nulo ni por defecto.",
                    "Se recibió código inválido: " + codigo
            );
        }

        List<SubcategoriaEntity> lista = daoFactory
                .getSubcategoriaDAO()
                .listByCodigo(codigo);

        if (lista.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se encontró la subcategoría con el código proporcionado.",
                    "listByCodigo retornó vacío para código: " + codigo
            );
        }
    }

    @Override
    public List<SubcategoriaDomain> consultarSubcategoria(final SubcategoriaDomain filtro) throws FondaControlException {
        final var entityFiltro = SubcategoriaEntityAssembler.getInstancia().toEntity(filtro);
        final List<SubcategoriaEntity> resultados = daoFactory.getSubcategoriaDAO().listByFilter(entityFiltro);

        return SubcategoriaEntityAssembler.getInstancia().toDomainList(resultados);
    }

    private void validarSubcategoria(final SubcategoriaDomain subcategoria) throws BusinessLogicFondaControlException {
        if (UtilObjeto.esNulo(subcategoria)) {
            throw BusinessLogicFondaControlException.reportar("La subcategoría no puede ser nula.");
        }
        if (UtilTexto.getInstancia().esNula(subcategoria.getNombre()) ||
                !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(subcategoria.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la subcategoría es inválido.");
        }
        if (UtilUUID.esValorDefecto(subcategoria.getCodigoCategoria())) {
            throw BusinessLogicFondaControlException.reportar("Debe especificar una categoría válida para la subcategoría.");
        }
    }
}
