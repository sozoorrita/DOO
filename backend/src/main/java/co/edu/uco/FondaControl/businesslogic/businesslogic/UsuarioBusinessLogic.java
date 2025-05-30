// ——— UsuarioBusinessLogic.java ———
package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;

public interface UsuarioBusinessLogic {
    void registrarNuevoUsuario(UsuarioDomain usuarioDomain) throws FondaControlException;
    void modificarUsuario(UsuarioDomain usuarioDomain) throws FondaControlException;
    void eliminarUsuario(UsuarioDomain usuarioDomain) throws FondaControlException;
    void iniciarSesion(UsuarioDomain usuarioDomain, String tipoUsuario) throws FondaControlException;

    List<UsuarioDomain> consultarUsuarios(UsuarioDomain filtro) throws FondaControlException;

    UsuarioDomain consultarUsuarioPorCodigo(UsuarioDomain usuarioDomain) throws FondaControlException;
}
