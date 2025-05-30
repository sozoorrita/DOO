package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

import java.util.List;


public interface InformeCajaFacade {
    void consolidarVentasInformeCaja(InformeCajaDTO informeCaja) throws FondaControlException;
    void crearInformeCaja(InformeCajaDTO informeCaja) throws FondaControlException;
    void eliminarInformeCaja(InformeCajaDTO informeCaja) throws FondaControlException;
    void consultarInformeCajaPorCodigo(InformeCajaDTO informeCaja) throws FondaControlException;
    List<InformeCajaDTO> consultarInformeCaja(InformeCajaDTO filtro) throws FondaControlException;
}
