package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.UsuarioDTO;

public interface UsuarioFacade {
    void registrarNuevoUsuario(UsuarioDTO usuario) throws FondaControlException;
    void modificarUsuario(UsuarioDTO usuario) throws FondaControlException;
    void eliminarUsuario(UsuarioDTO usuario) throws FondaControlException;
    void iniciarSesion(UsuarioDTO usuario, String tipoUsuario) throws FondaControlException;
}