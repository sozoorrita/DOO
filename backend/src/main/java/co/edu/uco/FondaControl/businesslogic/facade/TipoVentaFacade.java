package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.TipoVentaDTO;
import java.util.List;

public interface TipoVentaFacade {
	
    void registrarTipoVenta(TipoVentaDTO tipoVenta) throws FondaControlException;
    void modificarTipoVenta(TipoVentaDTO tipoVenta) throws FondaControlException;
    void eliminarTipoVenta(TipoVentaDTO tipoVenta) throws FondaControlException;
    List<TipoVentaDTO> consultarTipoVenta(TipoVentaDTO filtro) throws FondaControlException;
    
}