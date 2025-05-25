package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.MesaDTO;

import java.util.List;
import java.util.UUID;

public interface MesaFacade {

    void evaluarMesa(UUID codigo, MesaDTO mesa) throws FondaControlException;

    void configurarMesa(UUID codigo, MesaDTO mesa) throws FondaControlException;

    void registrarMesa(MesaDTO mesa) throws FondaControlException;

    List<MesaDTO> consultarMesa(UUID codigo) throws FondaControlException;
}
