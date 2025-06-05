package co.edu.uco.FondaControl.businesslogic.businesslogic;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface UsuarioBusinessLogic {
    void registrarNuevoUsuario(UsuarioDomain usuarioDomain) throws FondaControlException;
    void modificarUsuario(UsuarioDomain usuarioDomain) throws FondaControlException;
    void eliminarUsuario(UsuarioDomain usuarioDomain) throws FondaControlException;

    UUID iniciarSesion(UsuarioDomain usuarioDomain) throws FondaControlException;

    UsuarioDomain consultarUsuarioPorNombreYRol(String nombre, UUID codigoRol) throws FondaControlException;

    List<UsuarioDomain> consultarUsuarios(UsuarioDomain filtro) throws FondaControlException;

    UsuarioDomain consultarUsuarioPorCodigo(UsuarioDomain usuarioDomain) throws FondaControlException;
}
