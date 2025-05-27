package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.List;
import java.util.UUID;

public interface RolBusinessLogic {

    void registrarRol(RolDomain rolDomain) throws FondaControlException;

    void modificarRol(RolDomain rolDomain) throws FondaControlException;

    void eliminarRol(RolDomain rolDomain) throws FondaControlException;

    List<RolDomain> consultarRol(UUID codigo) throws FondaControlException;
}