package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.MesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.MesaDTO;

@RestController
@RequestMapping("/api/v8/mesas")
public class MesaController {

    private final MesaFacade mesaFacade;

    public MesaController(MesaFacade mesaFacade) {
        this.mesaFacade = mesaFacade;
    }

    @GetMapping("/dummy")
    public MesaDTO getDummy() {
        return new MesaDTO();
    }

    @GetMapping("/{id}")
    public List<MesaDTO> consultar(@PathVariable("id") UUID id) throws FondaControlException {
        return mesaFacade.consultarMesa(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody MesaDTO mesa) throws FondaControlException {
        mesaFacade.registrarMesa(mesa);
    }

    @PutMapping("/evaluar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void evaluar(@PathVariable("id") UUID id,
                        @RequestBody MesaDTO mesa) throws FondaControlException {
        mesaFacade.evaluarMesa(id, mesa);
    }

    @PutMapping("/configurar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void configurar(@PathVariable("id") UUID id,
                           @RequestBody MesaDTO mesa) throws FondaControlException {
        mesaFacade.configurarMesa(id, mesa);
    }
}
