package backend.src.main.java.co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;

public interface UsuarioBusinessLogic {
    void registrarNuevoUsuario(UsuarioDomain usuarioDomain);
    void modificarUsuario(UsuarioDomain usuarioDomain);
    void eliminarUsuario(UsuarioDomain usuarioDomain);
    void iniciarSesion(UsuarioDomain usuarioDomain, String tipoUsuario);
}