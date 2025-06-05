package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.time.Duration;
import java.time.LocalDateTime;
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
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.CategoriaEntity;

public final class CategoriaImpl implements CategoriaBusinessLogic {

    private final DAOFactory daoFactory;
    private static final long HORAS_MINIMAS_PARA_EDITAR = 2;

    public CategoriaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarCategoria(final CategoriaDomain categoria) throws FondaControlException {
        if (UtilObjeto.esNulo(categoria)) {
            throw BusinessLogicFondaControlException.reportar("No es posible registrar una categoría nula.");
        }

        if (UtilTexto.getInstancia().esNula(categoria.getNombre())
                || !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(categoria.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la categoría es inválido.");
        }

        categoria.setCodigo(UtilUUID.obtenerValorDefecto(categoria.getCodigo()));
        final CategoriaEntity entity = CategoriaEntityAssembler.getInstancia().toEntity(categoria);
        daoFactory.getCategoriaDAO().create(entity);
    }

    @Override
    public CategoriaDomain consultarCategoriaPorCodigo(final UUID codigo) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la categoría a consultar es inválido.");
        }

        CategoriaEntity entity = daoFactory.getCategoriaDAO().findById(codigo);
        if (UtilObjeto.esNulo(entity) || UtilUUID.esValorDefecto(entity.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar("No existe una categoría con el código proporcionado.");
        }

        return CategoriaEntityAssembler.getInstancia().toDomain(entity);
    }

    @Override
    public void modificarCategoria(final UUID codigo, final CategoriaDomain categoria) throws FondaControlException {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar("El código de la categoría a modificar es inválido.");
        }

        if (UtilObjeto.esNulo(categoria)
                || UtilTexto.getInstancia().esNula(categoria.getNombre())
                || !UtilTexto.getInstancia().contieneSoloLetrasYEspacios(categoria.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("La categoría a modificar es inválida.");
        }

        CategoriaEntity existente = daoFactory.getCategoriaDAO().findById(codigo);
        if (UtilObjeto.esNulo(existente)) {
            throw BusinessLogicFondaControlException.reportar("No existe la categoría a modificar.");
        }

        // Dentro de eliminarCategoria y modificarCategoria
        if (existente.getFechaEliminacion() != null) {
            throw BusinessLogicFondaControlException.reportar("La categoría ya se encuentra eliminada.");
        }

        long horasTranscurridas = Duration.between(existente.getFechaCreacion(), LocalDateTime.now()).toHours();
        if (horasTranscurridas < HORAS_MINIMAS_PARA_EDITAR) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se puede editar antes de " + HORAS_MINIMAS_PARA_EDITAR + " horas desde su creación.");
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

        CategoriaEntity existente = daoFactory.getCategoriaDAO().findById(codigo);
        if (UtilObjeto.esNulo(existente)) {
            throw BusinessLogicFondaControlException.reportar("No existe la categoría que se desea eliminar.");
        }

        if (existente.getFechaEliminacion() != null) {
            throw BusinessLogicFondaControlException.reportar("La categoría ya se encuentra eliminada.");
        }

        // *** Ya no seteamos fechaEliminacion aquí ni llamamos a update ***
        // existente.setFechaEliminacion(LocalDateTime.now());
        // daoFactory.getCategoriaDAO().update(codigo, existente);

        // En su lugar, llamamos al método delete(UUID) del DAO,
        // que internamente ejecuta SQL_SOFT_DELETE para marcar fecha_eliminacion=NOW().
        daoFactory.getCategoriaDAO().delete(codigo);
    }


    @Override
    public List<CategoriaDomain> consultarCategoria(final CategoriaDomain filtro) throws FondaControlException {
        final CategoriaEntity entityFiltro = CategoriaEntityAssembler.getInstancia().toEntity(filtro);
        final List<CategoriaEntity> resultados = daoFactory.getCategoriaDAO().listByFilter(entityFiltro);
        return CategoriaEntityAssembler.getInstancia().toDomainList(resultados);
    }
}