package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;
import java.util.UUID;

public interface TipoVentaBusinessLogic {

    void registrarTipoVenta(TipoVentaDomain tipoVentaDomain) throws FondaControlException;

    void modificarTipoVenta(TipoVentaDomain tipoVentaDomain) throws FondaControlException;

    void eliminarTipoVenta(TipoVentaDomain tipoVentaDomain) throws FondaControlException;

    List<TipoVentaDomain> consultarTipoVenta(UUID codigo) throws FondaControlException;
}