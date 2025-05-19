package  co.edu.uco.FondaControl.businesslogic.businesslogic.impl;


import co.edu.uco.FondaControl.businesslogic.businesslogic.UsuarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.util.UUID;

public class UsuarioImp implements UsuarioBusinessLogic {

    private final UsuarioDAO usuarioDAO;

    public UsuarioImp(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void registrarNuevoUsuario(UsuarioDomain usuarioDomain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre(usuarioDomain.getNombre());
        entity.setContrasena(usuarioDomain.getContrasena());
        usuarioDAO.create(entity);
    }

    @Override
    public void modificarUsuario(UsuarioDomain usuarioDomain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuarioDomain.getId());
        entity.setNombre(usuarioDomain.getNombre());
        entity.setContrasena(usuarioDomain.getContrasena());
        usuarioDAO.update(entity, usuarioDomain.getId());
    }

    @Override
    public void eliminarUsuario(UsuarioDomain usuarioDomain) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuarioDomain.getId());
        usuarioDAO.delete(entity);
    }

    @Override
    public void iniciarSesion(UsuarioDomain usuarioDomain, String tipoUsuario) {
        // Aquí  implementar la lógica de validación de credenciales Por ejemplo, buscar al usuario en la base de datos y validar la contraseña.


    }
}