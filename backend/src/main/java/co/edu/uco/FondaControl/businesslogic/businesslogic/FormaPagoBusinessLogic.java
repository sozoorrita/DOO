package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;
import java.util.UUID;

public interface FormaPagoBusinessLogic {

    void registrarFormaPago(FormaPagoDomain formaPagoDomain) throws FondaControlException;

    void modificarFormaPago(FormaPagoDomain formaPagoDomain) throws FondaControlException;

    void eliminarFormaPago(FormaPagoDomain formaPagoDomain) throws FondaControlException;

    List<FormaPagoDomain> consultarFormaPago(UUID codigo) throws FondaControlException;
}