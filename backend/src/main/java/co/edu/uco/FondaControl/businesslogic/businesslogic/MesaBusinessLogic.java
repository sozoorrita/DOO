package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;
import java.util.UUID;

public interface MesaBusinessLogic {

    void evaluarMesa(UUID codigo, MesaDomain mesa) throws FondaControlException;

    void configurarMesa(UUID codigo, MesaDomain mesa) throws FondaControlException;

    void registrarMesa(MesaDomain mesa) throws FondaControlException;

    void eliminarmesa(UUID codigo) throws FondaControlException;

    List<MesaDomain> consultarMesa(UUID codigo) throws FondaControlException;
}
