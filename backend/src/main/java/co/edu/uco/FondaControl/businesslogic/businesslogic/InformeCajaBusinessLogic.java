package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;


public interface InformeCajaBusinessLogic {

    void crearInformeCaja(InformeCajaDomain informeCajaDomain) throws FondaControlException;
    void eliminarInformeCaja(InformeCajaDomain informeCajaDomain) throws FondaControlException;
    void consultarInformeCajaPorcodigo(InformeCajaDomain informeCajaDomain) throws FondaControlException;
    List<InformeCajaDomain> consultarInformeCaja(InformeCajaDomain filtro) throws FondaControlException;
    void consolidarventasInformeCaja(InformeCajaDomain informeCajaDomain) throws FondaControlException;
}
