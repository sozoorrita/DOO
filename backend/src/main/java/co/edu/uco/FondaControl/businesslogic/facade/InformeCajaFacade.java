package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;


public interface InformeCajaFacade {
    void consolidarVentasInformeCaja(InformeCajaDTO informeCaja) throws FondaControlException;
}
