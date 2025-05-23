package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;

import java.util.List;
import java.util.UUID;

public interface IndicadorInventarioFacade {

    void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDTO indicadorInventario) throws FondaControlException;
    void configurarIndicadorInventario(UUID codigo,IndicadorInventarioDTO indicadorInventario) throws FondaControlException;
    void registrarIndicadorInventario(IndicadorInventarioDTO indicadorInventario) throws FondaControlException;
    List<IndicadorInventarioDTO> consultarIndicadorInventario(IndicadorInventarioDTO Filtro) throws FondaControlException;
}
