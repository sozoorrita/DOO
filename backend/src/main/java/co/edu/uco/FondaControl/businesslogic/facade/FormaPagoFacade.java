package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.FormaPagoDTO;
import java.util.List;

public interface FormaPagoFacade {
   
    void registrarFormaPago(FormaPagoDTO formaPago) throws FondaControlException;
    void modificarFormaPago(FormaPagoDTO formaPago) throws FondaControlException;
    void eliminarFormaPago(FormaPagoDTO formaPago) throws FondaControlException;
    List<FormaPagoDTO> consultarFormaPago(FormaPagoDTO filtro) throws FondaControlException;
    
}