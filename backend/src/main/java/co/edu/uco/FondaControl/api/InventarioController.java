package co.edu.uco.FondaControl.api;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.InventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InventarioDTO;

@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    private final InventarioFacade inventarioFacade;

    public InventarioController(InventarioFacade inventarioFacade) {
        this.inventarioFacade = inventarioFacade;
    }

    @GetMapping("/{id}/cantidad")
    public ResponseEntity<Void> consultarCantidad(@PathVariable("id") UUID id)
            throws FondaControlException {
        inventarioFacade.consultarCantidadInventario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> consultarInventario(@PathVariable("id") UUID id)
            throws FondaControlException {
        InventarioDTO dto = new InventarioDTO();
        dto.setCodigo(id);
        inventarioFacade.consultarInventario(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> registrarInventario(
            @RequestBody InventarioDTO inventario) throws FondaControlException {
        inventarioFacade.registrarInventario(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventario);
    }

    @PostMapping("/gestionar")
    public ResponseEntity<InventarioDTO> gestionarInventarioManualmente(
            @RequestBody InventarioDTO inventario) throws FondaControlException {
        inventarioFacade.gestionarInventarioManualmente(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCantidad(
            @PathVariable("id") UUID id,
            @RequestBody InventarioDTO inventario) throws FondaControlException {
        inventarioFacade.actualizarCantidadEnInventario(id, inventario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable("id") UUID id)
            throws FondaControlException {
        inventarioFacade.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }
}
