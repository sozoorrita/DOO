package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InventarioDTO;

import java.util.UUID;

public interface InventarioFacade {
    void actualizarCantidadEnInventario(UUID codigo,InventarioDTO inventario) throws FondaControlException;
    void consultarCantidadInventario(UUID codigo) throws FondaControlException;
    void gestionarInventarioManualmente(InventarioDTO inventario) throws FondaControlException;
}