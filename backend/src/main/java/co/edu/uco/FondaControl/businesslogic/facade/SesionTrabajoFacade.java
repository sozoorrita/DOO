package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.SesionTrabajoDTO;

public interface SesionTrabajoFacade {
    void iniciarSesionTrabajo(SesionTrabajoDTO sesionTrabajo) throws FondaControlException;
    void cerrarSesionTrabajo(SesionTrabajoDTO sesionTrabajo) throws FondaControlException;
}
