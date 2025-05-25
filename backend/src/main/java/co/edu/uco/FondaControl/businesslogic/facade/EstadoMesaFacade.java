package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;

import java.util.List;
import java.util.UUID;

public interface EstadoMesaFacade {

    void evaluarEstadoMesa(UUID codigo, EstadoMesaDTO estadoMesa) throws FondaControlException;

    void configurarEstadoMesa(UUID codigo, EstadoMesaDTO estadoMesa) throws FondaControlException;

    void registrarEstadoMesa(EstadoMesaDTO estadoMesa) throws FondaControlException;

    List<EstadoMesaDTO> consultarEstadoMesa(UUID codigo) throws FondaControlException;
}
