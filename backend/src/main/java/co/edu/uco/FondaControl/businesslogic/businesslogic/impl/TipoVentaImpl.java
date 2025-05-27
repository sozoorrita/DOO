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
    public void evaluarTipoVenta(final UUID codigo, final TipoVentaDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del tipo de venta no puede ser nulo para evaluar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El tipo de venta a evaluar no puede ser nulo.");
        }

        validarIntegridadNombreTipoVenta(domain.getNombre());
    }

    @Override
    public void configurarTipoVenta(final UUID codigo, final TipoVentaDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del tipo de venta no puede ser nulo para configurar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El tipo de venta a configurar no puede ser nulo.");
        }

        validarIntegridadNombreTipoVenta(domain.getNombre());

        final var entity = TipoVentaEntityAssembler.getInstance().toEntity(domain);
        factory.getTipoVentaDAO().update(codigo, entity);
    }

    @Override
    public void registrarTipoVenta(final TipoVentaDomain domain) throws FondaControlException {
        validarIntegridadNombreTipoVenta(domain.getNombre());

        validarNoExistaTipoVentaConMismoNombre(domain.getNombre());

        final var codigo = generarNuevoCodigoTipoVenta();
        final var domainACrear = TipoVentaDomain.crear(
                codigo,
                UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre())
        );

        final var entity = TipoVentaEntityAssembler.getInstance().toEntity(domainACrear);
        factory.getTipoVentaDAO().create(entity);
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

    private void validarIntegridadNombreTipoVenta(final String nombre) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del tipo de venta es obligatorio.");
        }
        if (UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre).length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del tipo de venta supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombre)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del tipo de venta solo puede contener letras y espacios.");
        }
    }

    private void validarNoExistaTipoVentaConMismoNombre(final String nombre) throws BusinessLogicFondaControlException, DataFondaControlException {
        final var filtro = TipoVentaEntity.builder()
                .nombre(nombre)
                .crear();

        final var resultado = factory.getTipoVentaDAO().listByFilter(filtro);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                    "Ya existe un tipo de venta con el mismo nombre.");
        }
    }

    private UUID generarNuevoCodigoTipoVenta() throws DataFondaControlException {
        UUID nuevoCodigo;
        boolean existeCodigo;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            final var resultado = factory.getTipoVentaDAO().listByCodigo(nuevoCodigo);
            existeCodigo = !resultado.isEmpty();
        } while (existeCodigo);

        return nuevoCodigo;
    }
}