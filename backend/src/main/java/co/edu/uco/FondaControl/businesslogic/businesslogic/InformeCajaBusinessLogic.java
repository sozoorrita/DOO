package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;


public interface InformeCajaBusinessLogic {
    void consolidarventasInformeCaja(InformeCajaDomain informeCajaDomain) throws FondaControlException;
}
