package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.SesionTrabajoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.time.LocalDateTime;

public class SesionTrabajoImp implements SesionTrabajoBusinessLogic {

    private final DAOFactory factory;

    public SesionTrabajoImp(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void iniciarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) {
        if (UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain) || UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain.getIdUsuario())) {
            return;
        }

        SesionTrabajoEntity sesionExistente = factory.getSesionTrabajoDAO().findByUsuario(sesionTrabajoDomain.getIdUsuario().getId());

        if (!UtilObjeto.getInstancia().esNulo(sesionExistente) && sesionExistente.getFechaCierre() == null) {
            return; // Ya existe una sesión abierta para este usuario
        }

        SesionTrabajoEntity nuevaSesion = new SesionTrabajoEntity();
        nuevaSesion.setCodigo(sesionTrabajoDomain.getCodigo());
        nuevaSesion.setIdUsuario(sesionTrabajoDomain.getIdUsuario());
        nuevaSesion.setBaseCaja(sesionTrabajoDomain.getBaseCaja());
        nuevaSesion.setFechaApertura(LocalDateTime.now());

        factory.getSesionTrabajoDAO().create(nuevaSesion);
    }

    @Override
    public void cerrarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) {
        if (UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain) || UtilObjeto.getInstancia().esNulo(sesionTrabajoDomain.getCodigo())) {
            return;
        }

        SesionTrabajoEntity sesionExistente = factory.getSesionTrabajoDAO().findById(sesionTrabajoDomain.getCodigo());

        if (UtilObjeto.getInstancia().esNulo(sesionExistente) || sesionExistente.getFechaCierre() != null) {
            return; // La sesión no existe o ya está cerrada
        }

        sesionExistente.setFechaCierre(LocalDateTime.now());
        factory.getSesionTrabajoDAO().update(sesionExistente.getCodigo(), sesionExistente);
    }
}