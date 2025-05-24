package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SesionTrabajoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.LayerException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.time.LocalDateTime;

public class SesionTrabajoImp implements SesionTrabajoBusinessLogic {

    private final DAOFactory factory;

    public SesionTrabajoImp(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void iniciarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException {
        if (UtilObjeto.esNulo(sesionTrabajoDomain) || UtilObjeto.esNulo(sesionTrabajoDomain.getUsuario())) {
            throw new FondaControlException(
                    "No se puede iniciar sesión de trabajo porque falta información.",
                    "La entidad SesionTrabajoDomain o el usuario asociado es nulo.",
                    null,
                    LayerException.BUSINESS_LOGIC
            );
        }

        var sesionExistente = factory.getSesionTrabajoDAO()
                .findByUsuario(sesionTrabajoDomain.getUsuario().getId());

        if (!UtilObjeto.esNulo(sesionExistente) && sesionExistente.getFechaCierre() == null) {
            throw new FondaControlException(
                    "Ya existe una sesión de trabajo abierta para este usuario.",
                    "Intento de iniciar una nueva sesión de trabajo cuando ya hay una activa para el usuario con ID: "
                            + sesionTrabajoDomain.getUsuario().getId(),
                    null,
                    LayerException.BUSINESS_LOGIC
            );
        }

        UsuarioDomain usuarioDomain = sesionTrabajoDomain.getUsuario();
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(usuarioDomain.getId());
        usuarioEntity.setNombre(usuarioDomain.getNombre());
        usuarioEntity.setCodigoRol(usuarioDomain.getCodigoRol());
        usuarioEntity.setContrasena(usuarioDomain.getContrasena());

        SesionTrabajoEntity nuevaSesion = new SesionTrabajoEntity();
        nuevaSesion.setIdUsuario(usuarioEntity);
        nuevaSesion.setBaseCaja(sesionTrabajoDomain.getBaseCaja());
        nuevaSesion.setFechaApertura(LocalDateTime.now());

        factory.getSesionTrabajoDAO().create(nuevaSesion);
    }

    @Override
    public void cerrarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException {
        if (UtilObjeto.esNulo(sesionTrabajoDomain) || UtilObjeto.esNulo(sesionTrabajoDomain.getCodigo())) {
            throw new FondaControlException(
                    "No se puede cerrar sesión de trabajo porque falta información.",
                    "La entidad SesionTrabajoDomain o su código es nulo.",
                    null,
                    LayerException.BUSINESS_LOGIC
            );
        }

        var sesionExistente = factory.getSesionTrabajoDAO().findById(sesionTrabajoDomain.getCodigo());

        if (UtilObjeto.esNulo(sesionExistente) || sesionExistente.getFechaCierre() != null) {
            throw new FondaControlException(
                    "No se puede cerrar la sesión porque no existe o ya fue cerrada.",
                    "No se encontró la sesión con el ID especificado o ya tiene fecha de cierre.",
                    null,
                    LayerException.BUSINESS_LOGIC
            );
        }

        sesionExistente.setFechaCierre(LocalDateTime.now());
        factory.getSesionTrabajoDAO().update(sesionExistente.getCodigo(), sesionExistente);
    }
}
