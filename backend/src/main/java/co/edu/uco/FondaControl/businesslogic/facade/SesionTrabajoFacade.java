package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.dto.SesionTrabajoDTO;

public interface SesionTrabajoFacade {
    void iniciarSesionTrabajo(SesionTrabajoDTO sesionTrabajo);
    void cerrarSesionTrabajo(SesionTrabajoDTO sesionTrabajo);
}
