package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;

import java.util.List;
import java.util.UUID;

public interface IndicadorInventarioBusinessLogic {

    void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain);
    void configurarIndicadorInventario(UUID codigo,IndicadorInventarioDomain indicadorInventarioDomain);
    void registrarIndicadorInventario(IndicadorInventarioDomain indicadorInventarioDomain);
    List<IndicadorInventarioDomain> consultarIndicadorInventario(UUID codigo);
}
