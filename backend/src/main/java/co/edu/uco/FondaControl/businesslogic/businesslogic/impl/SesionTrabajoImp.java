package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SesionTrabajoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.time.LocalDateTime;


public class SesionTrabajoImp implements SesionTrabajoBusinessLogic {

    private final DAOFactory factory;

    public SesionTrabajoImp(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void iniciarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain) || UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain.getIdUsuario())) {
            return;
        }

        // Verifica si ya hay una sesión activa para el usuario
        var sesionExistente = factory.getSesionTrabajoDAO().findByUsuario(sesionTrabajoDomain.getIdUsuario().getId());
        if (!UtilObjeto.esNulo(sesionExistente) && sesionExistente.getFechaCierre() == null) {
            return; // Ya existe una sesión abierta
        }

        // Conversión: UsuarioDomain → UsuarioEntity
        UsuarioDomain usuarioDomain = sesionTrabajoDomain.getIdUsuario();
        UsuarioEntity usuarioEntity = new UsuarioEntity(
                usuarioDomain.getId(),
                usuarioDomain.getNombre(),
                usuarioDomain.getCodigoRol(),
                usuarioDomain.getContrasena()
        );

        // Crear nueva sesión
        var nuevaSesion = new SesionTrabajoEntity();
        nuevaSesion.setCodigo(sesionTrabajoDomain.getCodigo());
        nuevaSesion.setIdUsuario(usuarioEntity);
        nuevaSesion.setBaseCaja(sesionTrabajoDomain.getBaseCaja());
        nuevaSesion.setFechaApertura(LocalDateTime.now());

        factory.getSesionTrabajoDAO().create(nuevaSesion);
    }

    @Override
    public void cerrarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain) || UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain.getCodigo())) {
            return;
        }

        var sesionExistente = factory.getSesionTrabajoDAO().findById(sesionTrabajoDomain.getCodigo());
        if (UtilObjeto.esNulo(sesionExistente) || sesionExistente.getFechaCierre() != null) {
            return; // La sesión no existe o ya está cerrada
        }

        sesionExistente.setFechaCierre(LocalDateTime.now());
        factory.getSesionTrabajoDAO().update(sesionExistente.getCodigo(), sesionExistente);
    }
}
