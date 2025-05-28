package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.EstadoMesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;

@RestController
@RequestMapping("/api/v1/estado-mesas")
public class EstadoMesaController {

    private final EstadoMesaFacade estadoMesaFacade;

    public EstadoMesaController(EstadoMesaFacade estadoMesaFacade) {
        this.estadoMesaFacade = estadoMesaFacade;
    }

    
    @GetMapping("/dummy")
    public EstadoMesaDTO getDummy() {
        return new EstadoMesaDTO();
    }

   
    @GetMapping
    public ResponseEntity<List<EstadoMesaDTO>> consultarTodos() throws FondaControlException {
        List<EstadoMesaDTO> lista = estadoMesaFacade.consultarEstadoMesa(null);
        return ResponseEntity.ok(lista);
    }

    
    @GetMapping("/{mesaId}")
    public ResponseEntity<List<EstadoMesaDTO>> consultarPorMesa(@PathVariable("mesaId") UUID mesaId)
            throws FondaControlException {
        List<EstadoMesaDTO> lista = estadoMesaFacade.consultarEstadoMesa(mesaId);
        return ResponseEntity.ok(lista);
    }

    
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody EstadoMesaDTO estado)
            throws FondaControlException {
        estadoMesaFacade.registrarEstadoMesa(estado);
        String mensaje = "El estado de mesa \"" + estado.getNombre() + "\" ha sido registrado exitosamente.";
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody EstadoMesaDTO estado)
            throws FondaControlException {
        estado.setCodigo(id);
        estadoMesaFacade.modificarEstadoMesa(estado);
        String mensaje = "El estado de mesa \"" + estado.getNombre() + "\" ha sido modificado exitosamente.";
        return ResponseEntity.ok(mensaje);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") UUID id)
            throws FondaControlException {
        EstadoMesaDTO dto = new EstadoMesaDTO();
        dto.setCodigo(id);
        estadoMesaFacade.eliminarEstadoMesa(dto);
        String mensaje = "El estado de mesa con código " + id + " ha sido eliminado exitosamente.";
        return ResponseEntity.ok(mensaje);
    }
}
