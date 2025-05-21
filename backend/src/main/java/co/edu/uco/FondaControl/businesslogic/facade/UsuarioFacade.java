package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.dto.UsuarioDTO;

public interface UsuarioFacade {
    void registrarNuevoUsuario(UsuarioDTO usuario);
    void modificarUsuario(UsuarioDTO usuario);
    void eliminarUsuario(UsuarioDTO usuario);
    void iniciarSesion(UsuarioDTO usuario, String tipoUsuario);
}