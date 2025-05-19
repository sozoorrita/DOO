package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;

import java.util.UUID;

public interface InventarioBusinessLogic {
    void actualizarCantidadEnInventario(InventarioDomain inventarioDomain);
    void consultarCantidadInventario(UUID codigo);
    void gestionarInventarioManualmente(InventarioDomain inventarioDomain);
}