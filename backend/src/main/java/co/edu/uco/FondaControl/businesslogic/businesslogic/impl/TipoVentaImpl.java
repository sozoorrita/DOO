package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.TipoVentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.TipoVenta.entity.TipoVentaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.TipoVentaEntity;

import java.util.List;
import java.util.UUID;

public final class TipoVentaImpl implements TipoVentaBusinessLogic {

    private final DAOFactory factory;

    public TipoVentaImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void registrarTipoVenta(final TipoVentaDomain tipoVentaDomain) throws FondaControlException {
        validarNombreTipoVenta(tipoVentaDomain.getNombre());
        validarNoExistaTipoVentaConMismoNombre(tipoVentaDomain.getNombre());

        final UUID codigo = UtilUUID.generarNuevoUUID();
        final TipoVentaEntity entity = TipoVentaEntityAssembler.getInstance().toEntity(TipoVentaDomain.crear(codigo, tipoVentaDomain.getNombre()));
        factory.getTipoVentaDAO().create(entity);
    }

    @Override
    public void modificarTipoVenta(final TipoVentaDomain tipoVentaDomain) throws FondaControlException {
        validarNombreTipoVenta(tipoVentaDomain.getNombre());

        final TipoVentaEntity entity = TipoVentaEntityAssembler.getInstance().toEntity(tipoVentaDomain);
        factory.getTipoVentaDAO().update(tipoVentaDomain.getCodigo(), entity);
    }

    @Override
    public void eliminarTipoVenta(final TipoVentaDomain tipoVentaDomain) throws FondaControlException {
        if (UtilUUID.esValorDefecto(tipoVentaDomain.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar("El código del tipo de venta es inválido para eliminar.");
        }

        factory.getTipoVentaDAO().delete(tipoVentaDomain.getCodigo());
    }

    @Override
    public List<TipoVentaDomain> consultarTipoVenta(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return TipoVentaEntityAssembler.getInstance()
                    .toDomainList(factory.getTipoVentaDAO().listAll());
        }
        return TipoVentaEntityAssembler.getInstance()
                .toDomainList(factory.getTipoVentaDAO().listByCodigo(codigo));
    }

    private void validarNombreTipoVenta(final String nombre) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del tipo de venta es obligatorio.");
        }
        if (nombre.length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El nombre del tipo de venta supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del tipo de venta solo puede contener letras y espacios.");
        }
    }

    private void validarNoExistaTipoVentaConMismoNombre(final String nombre) throws FondaControlException {
        final var filtro = TipoVentaEntity.builder().nombre(nombre).crear();

        final var resultado = factory.getTipoVentaDAO().listByFilter(filtro);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar("Ya existe un tipo de venta con el mismo nombre.");
        }
    }
}
