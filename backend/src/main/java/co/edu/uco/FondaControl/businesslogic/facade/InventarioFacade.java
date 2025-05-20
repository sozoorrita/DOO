package co.edu.uco.FondaControl.businesslogic.facade;

import co.edu.uco.FondaControl.dto.InventarioDTO;

import java.util.UUID;

public interface InventarioFacade {
    void actualizarCantidadEnInventario(InventarioDTO inventario);
    void consultarCantidadInventario(UUID codigo);
    void gestionarInventarioManualmente(InventarioDTO inventario);
}