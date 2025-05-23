package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

public interface SesionTrabajoBusinessLogic {
    void iniciarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException;
    void cerrarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain) throws FondaControlException;
}
