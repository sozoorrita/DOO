package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;
import java.util.UUID;

public interface IndicadorInventarioBusinessLogic {

    void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) throws FondaControlException;

    void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) throws FondaControlException;

    void registrarIndicadorInventario(IndicadorInventarioDomain indicadorInventarioDomain) throws FondaControlException;

    List<IndicadorInventarioDomain> consultarIndicadorInventario(IndicadorInventarioDomain indicadorInventarioDomain) throws FondaControlException;

    void eliminarIndicadorInventario(UUID codigo) throws FondaControlException;
    void modificarIndicadorInventario(UUID codigo, IndicadorInventarioDomain indicadorInventarioDomain) throws FondaControlException;

}