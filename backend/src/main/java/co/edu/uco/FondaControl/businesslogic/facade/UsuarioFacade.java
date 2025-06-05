package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.UsuarioDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioFacade {
    void registrarNuevoUsuario(UsuarioDTO usuario) throws FondaControlException;
    void modificarUsuario(UsuarioDTO usuario) throws FondaControlException;
    void eliminarUsuario(UsuarioDTO usuario) throws FondaControlException;

    UUID iniciarSesion(UsuarioDTO usuario) throws FondaControlException;

    List<UsuarioDTO> consultarUsuarios(UsuarioDTO filtro) throws FondaControlException;
    UsuarioDTO consultarUsuarioPorCodigo(UUID codigo) throws FondaControlException;
}
