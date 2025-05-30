package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.MesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.MesaDTO;

@RestController
@RequestMapping("/api/v1/mesas")
public class MesaController {

    private final MesaFacade mesaFacade;

    public MesaController(MesaFacade mesaFacade) {
        this.mesaFacade = mesaFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MesaDTO>> consultarMesa(@PathVariable("id") UUID id)
            throws FondaControlException {
        List<MesaDTO> lista = mesaFacade.consultarMesa(id);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<MesaDTO> crearMesa(@RequestBody MesaDTO mesa) throws FondaControlException {
        mesaFacade.registrarMesa(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesa);
    }

    @PutMapping("/evaluar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void evaluarMesa(@PathVariable("id") UUID id,
                            @RequestBody MesaDTO mesa) throws FondaControlException {
        mesaFacade.evaluarMesa(id, mesa);
    }

    @PutMapping("/configurar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void configurarMesa(@PathVariable("id") UUID id,
                               @RequestBody MesaDTO mesa) throws FondaControlException {
        mesaFacade.configurarMesa(id, mesa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMesa(@PathVariable("id") UUID id) throws FondaControlException {
        mesaFacade.eliminarMesa(id);
    }
}

