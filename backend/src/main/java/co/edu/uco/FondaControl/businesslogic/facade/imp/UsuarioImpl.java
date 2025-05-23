package co.edu.uco.FondaControl.businesslogic.facade.imp;


import co.edu.uco.FondaControl.businesslogic.businesslogic.UsuarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.businesslogic.facade.UsuarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.UsuarioDTO;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

public class UsuarioImpl implements UsuarioFacade {

    private final UsuarioBusinessLogic businessLogic;

    public UsuarioImpl(UsuarioBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    @Override
    public void registrarNuevoUsuario(UsuarioDTO usuario) throws FondaControlException {
        if (UtilObjeto.esNulo(usuario)) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        UsuarioDomain domain = mapToDomain(usuario);
        businessLogic.registrarNuevoUsuario(domain);
    }

    @Override
    public void modificarUsuario(UsuarioDTO usuario) throws FondaControlException {
        if (UtilObjeto.esNulo(usuario)) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        UsuarioDomain domain = mapToDomain(usuario);
        businessLogic.modificarUsuario(domain);
    }

    @Override
    public void eliminarUsuario(UsuarioDTO usuario) throws FondaControlException {
        if (UtilObjeto.esNulo(usuario)) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        UsuarioDomain domain = mapToDomain(usuario);
        businessLogic.eliminarUsuario(domain);
    }

    @Override
    public void iniciarSesion(UsuarioDTO usuario, String tipoUsuario) throws FondaControlException {
        if (UtilObjeto.esNulo(usuario) || UtilTexto.getInstancia().esNula(tipoUsuario)) {
            throw new IllegalArgumentException("El usuario y el tipo de usuario no pueden ser nulos o vacíos.");
        }
        UsuarioDomain domain = mapToDomain(usuario);
        businessLogic.iniciarSesion(domain, tipoUsuario);
    }

    private UsuarioDomain mapToDomain(UsuarioDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return null;
        }
        return new UsuarioDomain(
                dto.getId(),
                dto.getNombre(),
                dto.getCodigoRol(),
                dto.getContrasena()
        );
    }

    private UsuarioDTO mapToDTO(UsuarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return null;
        }
        return new UsuarioDTO(
                domain.getId(),
                domain.getNombre(),
                domain.getCodigoRol(),
                domain.getContrasena()
        );
    }
}