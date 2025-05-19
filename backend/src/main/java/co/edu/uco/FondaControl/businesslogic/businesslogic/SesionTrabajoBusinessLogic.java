package backend.src.main.java.co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;

public interface SesionTrabajoBusinessLogic {
    void iniciarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain);
    void cerrarSesionTrabajo(SesionTrabajoDomain sesionTrabajoDomain);
}
