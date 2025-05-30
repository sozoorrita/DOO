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
public class UsuarioController {

    private final UsuarioFacade usuarioFacade;

    public UsuarioController(UsuarioFacade usuarioFacade) {
        this.usuarioFacade = usuarioFacade;
    }

    /**
     * Lista todos los usuarios.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> consultarUsuarios() throws FondaControlException {
        List<UsuarioDTO> lista = usuarioFacade.consultarUsuarios(new UsuarioDTO());
        return ResponseEntity.ok(lista);
    }

    /**
     * Consulta un usuario por su código.
     */
    @GetMapping("/{codigo}")
    public ResponseEntity<UsuarioDTO> consultarUsuarioPorCodigo(@PathVariable UUID codigo)
            throws FondaControlException {
        UsuarioDTO resultado = usuarioFacade.consultarUsuarioPorCodigo(codigo);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Registra un nuevo usuario.
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> registrarUsuario(
            @RequestBody UsuarioDTO usuario) throws FondaControlException {
        usuarioFacade.registrarNuevoUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    /**
     * Modifica un usuario existente.
     */
    @PutMapping("/{codigo}")
    public ResponseEntity<UsuarioDTO> modificarUsuario(
            @PathVariable UUID codigo,
            @RequestBody UsuarioDTO usuario) throws FondaControlException {
        usuario.setId(codigo);
        usuarioFacade.modificarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Elimina un usuario.
     */
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuario(@PathVariable UUID codigo) throws FondaControlException {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(codigo);
        usuarioFacade.eliminarUsuario(dto);
    }

    /**
     * Inicia sesión de un usuario.
     */
    @PostMapping("/iniciar-sesion")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void iniciarSesion(
            @RequestBody UsuarioDTO usuario,
            @RequestParam String tipoUsuario) throws FondaControlException {
        usuarioFacade.iniciarSesion(usuario, tipoUsuario);
    }
}