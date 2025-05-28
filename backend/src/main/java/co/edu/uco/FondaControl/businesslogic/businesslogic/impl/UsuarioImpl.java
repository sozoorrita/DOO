package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.UsuarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Usuario.entity.UsuarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import java.util.UUID;

public final class UsuarioImpl implements UsuarioBusinessLogic {

    private final DAOFactory factory;

    public UsuarioImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void registrarNuevoUsuario(final UsuarioDomain usuarioDomain) throws FondaControlException {
        validarIntegridadUsuario(usuarioDomain);
        validarNoExistaUsuarioConMismoId(usuarioDomain.getId());

        final var nuevoCodigo = generarNuevoCodigoUsuario();
        final var usuarioARegistrar = new UsuarioDomain(nuevoCodigo, usuarioDomain.getNombre(),
                usuarioDomain.getCodigoRol(), usuarioDomain.getContrasena());

        final var entity = UsuarioEntityAssembler.getInstance().toEntity(usuarioARegistrar);
        factory.getUsuarioDAO().create(entity);
    }

    @Override
    public void modificarUsuario(final UsuarioDomain usuarioDomain) throws FondaControlException {
        validarCodigo(usuarioDomain.getId());
        validarIntegridadUsuario(usuarioDomain);

        final var entity = UsuarioEntityAssembler.getInstance().toEntity(usuarioDomain);
        factory.getUsuarioDAO().update(usuarioDomain.getId(), entity);
    }

    @Override
    public void eliminarUsuario(final UsuarioDomain usuarioDomain) throws FondaControlException {
        validarCodigo(usuarioDomain.getId());
        factory.getUsuarioDAO().delete(usuarioDomain.getId());
    }

    @Override
    public void iniciarSesion(final UsuarioDomain usuarioDomain, final String tipoUsuario) throws FondaControlException {
        validarCodigo(usuarioDomain.getId());

        if (UtilTexto.getInstancia().esNula(usuarioDomain.getContrasena())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La contraseña no puede ser nula ni vacía.",
                    "Intento de login fallido con contraseña vacía para el usuario con ID: " + usuarioDomain.getId()
            );
        }

        final var usuarioExistente = factory.getUsuarioDAO().findById(usuarioDomain.getId());

        if (UtilObjeto.esNulo(usuarioExistente)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El usuario no existe.",
                    "No se encontró en base de datos un usuario con ID: " + usuarioDomain.getId()
            );
        }

        if (!usuarioExistente.getContrasena().equals(usuarioDomain.getContrasena())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La contraseña es incorrecta.",
                    "Contraseña incorrecta para usuario con ID: " + usuarioDomain.getId()
            );
        }
    }

    private void validarCodigo(final UUID codigo) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El ID del usuario no puede ser nulo ni por defecto.",
                    "ID del usuario recibido es nulo o UUID por defecto."
            );
        }
    }

    private void validarIntegridadUsuario(final UsuarioDomain usuarioDomain) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(usuarioDomain)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El usuario no puede ser nulo.",
                    "Intento de registrar o modificar un usuario con objeto Domain nulo."
            );
        }

        if (UtilTexto.getInstancia().esNula(usuarioDomain.getNombre())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del usuario es obligatorio.",
                    "Se recibió un nombre nulo o vacío para el usuario."
            );
        }

        if (usuarioDomain.getNombre().length() > 50) {
            throw BusinessLogicFondaControlException.reportar(
                    "El nombre del usuario no puede superar los 50 caracteres.",
                    "Nombre recibido: [" + usuarioDomain.getNombre() + "]"
            );
        }

        if (UtilTexto.getInstancia().esNula(usuarioDomain.getContrasena())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La contraseña del usuario no puede estar vacía.",
                    "Contraseña vacía al registrar/modificar usuario con ID: " + usuarioDomain.getId()
            );
        }
    }

    private void validarNoExistaUsuarioConMismoId(final UUID id) throws FondaControlException {
        final var resultado = factory.getUsuarioDAO().listByCodigo(id);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                    "Ya existe un usuario con el mismo ID. No es posible registrarlo nuevamente.",
                    "Intento de registrar usuario con ID ya existente en base de datos: " + id
            );
        }
    }

    private UUID generarNuevoCodigoUsuario() throws FondaControlException {
        UUID nuevoCodigo;
        boolean yaExiste;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            final var resultado = factory.getUsuarioDAO().listByCodigo(nuevoCodigo);
            yaExiste = !resultado.isEmpty();
        } while (yaExiste);

        return nuevoCodigo;
    }
}