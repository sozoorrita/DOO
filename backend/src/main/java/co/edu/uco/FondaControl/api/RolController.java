package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.RolFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.RolDTO;

@RestController
@RequestMapping("/api/v10/roles")
public class RolController {

    private final RolFacade rolFacade;

    public RolController(RolFacade rolFacade) {
        this.rolFacade = rolFacade;
    }

    
    @GetMapping("/dummy")
    public RolDTO dummy() {
        return new RolDTO.Builder().crear();
    }

    
    @GetMapping
    public ResponseEntity<List<RolDTO>> consultar(
            @RequestBody(required = false) RolDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = dummy();
        }
        List<RolDTO> lista = rolFacade.consultarRol(filtro);
        return ResponseEntity.ok(lista);
    }

    
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody RolDTO rol)
            throws FondaControlException {
        rolFacade.registrarRol(rol);
        String msg = "El rol \"" + rol.getNombre() + "\" ha sido registrado exitosamente.";
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody RolDTO rol)
            throws FondaControlException {
        rol.setCodigo(id);
        rolFacade.modificarRol(rol);
        String msg = "El rol \"" + rol.getNombre() + "\" ha sido modificado exitosamente.";
        return ResponseEntity.ok(msg);
    }

    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        RolDTO dto = new RolDTO.Builder().codigo(id).crear();
        rolFacade.eliminarRol(dto);
    }
}
