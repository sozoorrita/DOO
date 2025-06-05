package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.UsuarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.UsuarioDTO;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UsuarioController {

    private final UsuarioFacade usuarioFacade;

    public UsuarioController(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    // Clase interna para el cuerpo del login
    public static class LoginRequest {
        public String nombre;
        public String contrasena;
        public String codigoRol;
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<UUID> iniciarSesion(@RequestBody LoginRequest request) {
        try {
            UsuarioDTO usuario = UsuarioDTO.builder()
                    .nombre(request.nombre)
                    .contrasena(request.contrasena)
                    .codigoRol(UUID.fromString(request.codigoRol))
                    .crear();

            UUID uuidSesion = usuarioFacade.iniciarSesion(usuario);
            return ResponseEntity.ok(uuidSesion);
        } catch (FondaControlException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody UsuarioDTO usuario) {
        try {
            usuarioFacade.registrarNuevoUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario registrado exitosamente.");
        } catch (FondaControlException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable UUID id, @RequestBody UsuarioDTO usuario) {
        try {
            usuario.setId(id);
            usuarioFacade.modificarUsuario(usuario);
            return ResponseEntity.ok("Usuario modificado correctamente.");
        } catch (FondaControlException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable UUID id) {
        try {
            UsuarioDTO usuario = UsuarioDTO.builder().id(id).crear();
            usuarioFacade.eliminarUsuario(usuario);
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } catch (FondaControlException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> consultarPorCodigo(@PathVariable UUID id) {
        try {
            UsuarioDTO usuario = usuarioFacade.consultarUsuarioPorCodigo(id);
            return ResponseEntity.ok(usuario);
        } catch (FondaControlException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> consultarTodos() {
        try {
            List<UsuarioDTO> usuarios = usuarioFacade.consultarUsuarios(new UsuarioDTO());
            return ResponseEntity.ok(usuarios);
        } catch (FondaControlException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
