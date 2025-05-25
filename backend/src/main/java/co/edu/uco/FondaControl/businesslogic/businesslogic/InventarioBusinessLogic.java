package co.edu.uco.FondaControl.businesslogic.businesslogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;

import java.util.UUID;

public interface InventarioBusinessLogic {
    void actualizarCantidadEnInventario(UUID codigo,InventarioDomain inventario) throws FondaControlException;
    void consultarCantidadInventario(UUID codigo) throws FondaControlException;
    void gestionarInventarioManualmente(InventarioDomain inventarioDomain) throws FondaControlException;
}