// src/main/java/co/edu/uco/FondaControl/api/RolController.java

package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.RolFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.RolDTO;

/**
 * Controlador REST para operaciones CRUD sobre Roles.
 *
 * Ahora delega toda la apertura y cierre de conexiones a RolFacade,
 * que internamente invoca a DAOFactory y se encarga de cerrar siempre.
 */
@RestController
@RequestMapping("/api/v1/roles")
public class RolController {

    private final RolFacade rolFacade;

    public RolController(RolFacade rolFacade) {
        this.rolFacade = rolFacade;
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> listarRoles() throws FondaControlException {
        // RolFacade se encarga de abrir y cerrar la conexión internamente
        List<RolDTO> lista = rolFacade.consultarRol(new RolDTO.Builder().crear());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerRol(@PathVariable("id") UUID id) throws FondaControlException {
        RolDTO filtro = new RolDTO.Builder().codigo(id).crear();
        List<RolDTO> encontrados = rolFacade.consultarRol(filtro);
        if (encontrados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(encontrados.get(0));
    }

    @PostMapping
    public ResponseEntity<RolDTO> crearRol(@RequestBody RolDTO rol) throws FondaControlException {
        rolFacade.registrarRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizarRol(@PathVariable("id") UUID id,
                                                @RequestBody RolDTO rol) throws FondaControlException {
        rol.setCodigo(id);
        rolFacade.modificarRol(rol);
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarRol(@PathVariable("id") UUID id) throws FondaControlException {
        RolDTO filtro = new RolDTO.Builder().codigo(id).crear();
        rolFacade.eliminarRol(filtro);
    }
}
