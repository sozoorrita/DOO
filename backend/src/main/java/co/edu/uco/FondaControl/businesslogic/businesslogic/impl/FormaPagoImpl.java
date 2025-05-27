package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.FormaPagoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.FormaPago.entity.FormaPagoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.FormaPagoEntity;

import java.util.List;
import java.util.UUID;

public final class FormaPagoImpl implements FormaPagoBusinessLogic {

    private final DAOFactory factory;

    public FormaPagoImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarFormaPago(final UUID codigo, final FormaPagoDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código de la forma de pago no puede ser nulo para evaluar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("La forma de pago a evaluar no puede ser nula.");
        }

        validarIntegridadNombreFormaPago(domain.getNombre());
    }

    @Override
    public void configurarFormaPago(final UUID codigo, final FormaPagoDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código de la forma de pago no puede ser nulo para configurar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("La forma de pago a configurar no puede ser nula.");
        }

        validarIntegridadNombreFormaPago(domain.getNombre());

        final var entity = FormaPagoEntityAssembler.getInstance().toEntity(domain);
        factory.getFormaPagoDAO().update(codigo, entity);
    }

    @Override
    public void registrarFormaPago(final FormaPagoDomain domain) throws FondaControlException {
        validarIntegridadNombreFormaPago(domain.getNombre());

        validarNoExistaFormaPagoConMismoNombre(domain.getNombre());

        final var codigo = generarNuevoCodigoFormaPago();
        final var domainACrear = FormaPagoDomain.crear(
                codigo,
                UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre())
        );

        final var entity = FormaPagoEntityAssembler.getInstance().toEntity(domainACrear);
        factory.getFormaPagoDAO().create(entity);
    }

    @Override
    public List<FormaPagoDomain> consultarFormaPago(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return FormaPagoEntityAssembler.getInstance()
                    .toDomainList(factory.getFormaPagoDAO().listAll());
        }
        return FormaPagoEntityAssembler.getInstance()
                .toDomainList(factory.getFormaPagoDAO().listByCodigo(codigo));
    }

    private void validarIntegridadNombreFormaPago(final String nombre) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre de la forma de pago es obligatorio.");
        }
        if (UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre).length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre de la forma de pago supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombre)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre de la forma de pago solo puede contener letras y espacios.");
        }
    }

    private void validarNoExistaFormaPagoConMismoNombre(final String nombre) throws BusinessLogicFondaControlException, DataFondaControlException {
        final var filtro = FormaPagoEntity.builder()
                .nombre(nombre)
                .crear();

        final var resultado = factory.getFormaPagoDAO().listByFilter(filtro);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                    "Ya existe una forma de pago con el mismo nombre.");
        }
    }

    private UUID generarNuevoCodigoFormaPago() throws DataFondaControlException {
        UUID nuevoCodigo;
        boolean existeCodigo;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            final var resultado = factory.getFormaPagoDAO().listByCodigo(nuevoCodigo);
            existeCodigo = !resultado.isEmpty();
        } while (existeCodigo);

        return nuevoCodigo;
    }
}