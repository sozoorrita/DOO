package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.UsuarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Usuario.dto.UsuarioDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.facade.UsuarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.UsuarioDTO;

public final class UsuarioImp implements UsuarioFacade {

    private final UsuarioBusinessLogic businessLogic;

    public UsuarioImp(final UsuarioBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    @Override
    public void registrarNuevoUsuario(final UsuarioDTO usuario) throws FondaControlException {
        validarUsuario(usuario);
        final var domain = UsuarioDTOAssembler.getInstancia().toDomain(usuario);
        businessLogic.registrarNuevoUsuario(domain);
    }

    @Override
    public void modificarUsuario(final UsuarioDTO usuario) throws FondaControlException {
        validarUsuario(usuario);
        final var domain = UsuarioDTOAssembler.getInstancia().toDomain(usuario);
        businessLogic.modificarUsuario(domain);
    }

    @Override
    public void eliminarUsuario(final UsuarioDTO usuario) throws FondaControlException {
        validarUsuario(usuario);
        final var domain = UsuarioDTOAssembler.getInstancia().toDomain(usuario);
        businessLogic.eliminarUsuario(domain);
    }

    @Override
    public void iniciarSesion(final UsuarioDTO usuario, final String tipoUsuario) throws FondaControlException {
        if (UtilObjeto.esNulo(usuario) || UtilTexto.getInstancia().esNula(tipoUsuario)) {
            throw new IllegalArgumentException("El usuario y el tipo de usuario no pueden ser nulos o vacíos.");
        }

        try {
            final var domain = UsuarioDTOAssembler.getInstancia().toDomain(usuario);
            businessLogic.iniciarSesion(domain, tipoUsuario);
        } catch (Exception exception) {
            final var mensajeUsuario = "Se ha producido un error al intentar iniciar sesión.";
            final var mensajeTecnico = "Error técnico durante el inicio de sesión: " + exception.getMessage();

            throw BusinessLogicFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    private void validarUsuario(final UsuarioDTO usuario) {
        if (UtilObjeto.esNulo(usuario)) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
    }
}
