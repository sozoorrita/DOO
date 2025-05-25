package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;
import java.util.UUID;

public interface EstadoMesaBusinessLogic {

    void evaluarEstadoMesa(UUID codigo, EstadoMesaDomain estadoMesa) throws FondaControlException;

    void configurarEstadoMesa(UUID codigo, EstadoMesaDomain estadoMesa) throws FondaControlException;

    void registrarEstadoMesa(EstadoMesaDomain estadoMesa) throws FondaControlException;

    List<EstadoMesaDomain> consultarEstadoMesa(UUID codigo) throws FondaControlException;
}
