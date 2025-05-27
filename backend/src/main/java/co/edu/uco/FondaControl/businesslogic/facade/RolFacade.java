package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.RolDTO;
import java.util.List;

public interface RolFacade {
	
    void registrarRol(RolDTO rol) throws FondaControlException;
    void modificarRol(RolDTO rol) throws FondaControlException;
    void eliminarRol(RolDTO rol) throws FondaControlException;
    List<RolDTO> consultarRol(RolDTO filtro) throws FondaControlException;
    
}