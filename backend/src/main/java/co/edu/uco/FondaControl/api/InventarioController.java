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

    
    @GetMapping("/{id}")
    public ResponseEntity<Void> consultarCantidad(@PathVariable("id") UUID id)
            throws FondaControlException {
        inventarioFacade.consultarCantidadInventario(id);
        return ResponseEntity.ok().build();
    }

  
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void gestionarManualmente(@RequestBody InventarioDTO inventario)
            throws FondaControlException {
        inventarioFacade.gestionarInventarioManualmente(inventario);
    }

   
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void actualizarCantidad(@PathVariable("id") UUID id,
                                   @RequestBody InventarioDTO inventario)
            throws FondaControlException {
        inventarioFacade.actualizarCantidadEnInventario(id, inventario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) {
        throw new UnsupportedOperationException("Eliminación de inventario no soportada.");
    }
}
