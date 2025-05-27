package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.RolBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Rol.entity.RolEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.RolEntity;

import java.util.List;
import java.util.UUID;

public final class RolImpl implements RolBusinessLogic {

    private final DAOFactory factory;

    public RolImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void evaluarRol(final UUID codigo, final RolDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del rol no puede ser nulo para evaluar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El rol a evaluar no puede ser nulo.");
        }

        validarIntegridadNombreRol(domain.getNombre());
    }

    @Override
    public void configurarRol(final UUID codigo, final RolDomain domain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del rol no puede ser nulo para configurar.");
        }
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("El rol a configurar no puede ser nulo.");
        }

        validarIntegridadNombreRol(domain.getNombre());

        final var entity = RolEntityAssembler.getInstance().toEntity(domain);
        factory.getRolDAO().update(codigo, entity);
    }

    @Override
    public void registrarRol(final RolDomain domain) throws FondaControlException {
        validarIntegridadNombreRol(domain.getNombre());

        validarNoExistaRolConMismoNombre(domain.getNombre());

        final var codigo = generarNuevoCodigoRol();
        final var domainACrear = RolDomain.crear(
                codigo,
                UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre())
        );

        final var entity = RolEntityAssembler.getInstance().toEntity(domainACrear);
        factory.getRolDAO().create(entity);
    }

    @Override
    public List<RolDomain> consultarRol(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return RolEntityAssembler.getInstance()
                    .toDomainList(factory.getRolDAO().listAll());
        }
        return RolEntityAssembler.getInstance()
                .toDomainList(factory.getRolDAO().listByCodigo(codigo));
    }

    private void validarIntegridadNombreRol(final String nombre) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del rol es obligatorio.");
        }
        if (UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre).length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del rol supera los 50 caracteres permitidos.");
        }
        if (!UtilTexto.getInstancia().contieneSoloLetrasYEspacios(nombre)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del rol solo puede contener letras y espacios.");
        }
    }

    private void validarNoExistaRolConMismoNombre(final String nombre) throws BusinessLogicFondaControlException, DataFondaControlException {
        final var filtro = RolEntity.builder()
                .nombre(nombre)
                .crear();

        final var resultado = factory.getRolDAO().listByFilter(filtro);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                    "Ya existe un rol con el mismo nombre.");
        }
    }

    private UUID generarNuevoCodigoRol() throws DataFondaControlException {
        UUID nuevoCodigo;
        boolean existeCodigo;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            final var resultado = factory.getRolDAO().listByCodigo(nuevoCodigo);
            existeCodigo = !resultado.isEmpty();
        } while (existeCodigo);

        return nuevoCodigo;
    }
}