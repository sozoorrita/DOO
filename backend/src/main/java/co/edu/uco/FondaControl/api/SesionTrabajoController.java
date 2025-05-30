package co.edu.uco.FondaControl.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.SesionTrabajoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.SesionTrabajoDTO;

@RestController
@RequestMapping("/api/v1/sesion-trabajo")
public class SesionTrabajoController {

    private final SesionTrabajoFacade sesionTrabajoFacade;

    public SesionTrabajoController(SesionTrabajoFacade sesionTrabajoFacade) {
        this.sesionTrabajoFacade = sesionTrabajoFacade;
    }

    @PostMapping("/iniciar")
    @ResponseStatus(HttpStatus.CREATED)
    public void iniciarSesionTrabajo(@RequestBody SesionTrabajoDTO sesionDTO) throws FondaControlException {
        sesionTrabajoFacade.iniciarSesionTrabajo(sesionDTO);
    }

    @PutMapping("/cerrar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cerrarSesionTrabajo(@RequestBody SesionTrabajoDTO sesionDTO) throws FondaControlException {
        sesionTrabajoFacade.cerrarSesionTrabajo(sesionDTO);
    }

    @GetMapping
    public ResponseEntity<List<SesionTrabajoDTO>> consultarSesionTrabajo(
            @RequestBody(required = false) SesionTrabajoDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = new SesionTrabajoDTO();
        }
        List<SesionTrabajoDTO> lista = sesionTrabajoFacade.consultarSesionTrabajo(filtro);
        return ResponseEntity.ok(lista);
    }
}
