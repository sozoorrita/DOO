package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;

import java.util.List;
import java.util.UUID;

public interface IndicadorInventarioFacade {

    void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDTO indicadorInventario);
    void configurarIndicadorInventario(UUID codigo,IndicadorInventarioDTO indicadorInventario);
    void registrarIndicadorInventario(IndicadorInventarioDTO indicadorInventario);
    List<IndicadorInventarioDTO> consultarIndicadorInventario(IndicadorInventarioDTO Filtro);
}
