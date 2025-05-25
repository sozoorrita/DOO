package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import java.util.UUID;
import java.util.logging.Logger;

import co.edu.uco.FondaControl.businesslogic.businesslogic.UsuarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

public class UsuarioImpl implements UsuarioBusinessLogic {

    private static final Logger LOGGER = Logger.getLogger(UsuarioImpl.class.getName());

    private final UsuarioDAO usuarioDAO;

    public UsuarioImpl(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public void registrarNuevoUsuario(UsuarioDomain usuarioDomain) throws FondaControlException {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre(usuarioDomain.getNombre());
        entity.setContrasena(usuarioDomain.getContrasena());
        usuarioDAO.create(entity);
        LOGGER.info("Usuario registrado exitosamente: " + entity.getNombre());
    }

    @Override
    public void modificarUsuario(UsuarioDomain usuarioDomain) throws FondaControlException {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuarioDomain.getId());
        entity.setNombre(usuarioDomain.getNombre());
        entity.setContrasena(usuarioDomain.getContrasena());
        usuarioDAO.update(usuarioDomain.getId(), entity);
        LOGGER.info("Usuario modificado exitosamente: " + entity.getNombre());
    }

    @Override
    public void eliminarUsuario(UsuarioDomain usuarioDomain) throws FondaControlException {
        if (UtilObjeto.esNulo(usuarioDomain) || UtilObjeto.esNulo(usuarioDomain.getId())) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");
        }

        usuarioDAO.delete(usuarioDomain.getId());
        LOGGER.info("Usuario eliminado con ID: " + usuarioDomain.getId());
    }


    @Override
    public void iniciarSesion(UsuarioDomain usuarioDomain, String tipoUsuario) throws FondaControlException {
        if (UtilObjeto.esNulo(usuarioDomain) || UtilObjeto.esNulo(usuarioDomain.getId())
                || UtilTexto.getInstancia().esNula(usuarioDomain.getContrasena())) {
            throw new IllegalArgumentException("El usuario, su ID y su contraseña no pueden ser nulos o vacíos.");
        }

        UsuarioEntity usuarioEntity = usuarioDAO.findById(usuarioDomain.getId());
        if (UtilObjeto.esNulo(usuarioEntity)) {
            throw new IllegalArgumentException("El usuario no existe.");
        }

        if (!usuarioEntity.getContrasena().equals(usuarioDomain.getContrasena())) {
            throw new IllegalArgumentException("La contraseña es incorrecta.");
        }

        LOGGER.info("Inicio de sesión exitoso para el usuario: " + usuarioEntity.getNombre());
    }
}
