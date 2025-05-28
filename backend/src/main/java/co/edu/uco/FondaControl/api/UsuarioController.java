package co.edu.uco.FondaControl.api;

import org.springframework.http.HttpStatus;
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

   @GetMapping
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void consultar() {
        throw new UnsupportedOperationException("Consulta de usuarios no está soportada.");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody UsuarioDTO usuario) throws FondaControlException {
        usuarioFacade.registrarNuevoUsuario(usuario);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modificar(@RequestBody UsuarioDTO usuario) throws FondaControlException {
        usuarioFacade.modificarUsuario(usuario);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@RequestBody UsuarioDTO usuario) throws FondaControlException {
        usuarioFacade.eliminarUsuario(usuario);
    }

    @PostMapping("/iniciar-sesion")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void iniciarSesion(@RequestBody UsuarioDTO usuario,
                              @RequestParam String tipoUsuario)
            throws FondaControlException {
        usuarioFacade.iniciarSesion(usuario, tipoUsuario);
    }
}
