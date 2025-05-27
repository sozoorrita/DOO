package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v14/usuarios")
public class UsuarioController {

    @GetMapping
    public String consultar() {
        return "Consulta todos los usuarios";
    }

    @PostMapping
    public String registrar() {
        return "Registrar un nuevo usuario";
    }

    @PutMapping
    public String modificar() {
        return "Modificar un usuario";
    }

    @DeleteMapping
    public String eliminar() {
        return "Eliminar un usuario";
    }

    @PostMapping("/iniciar-sesion")
    public String iniciarSesion() {
        return "Iniciar sesión de usuario";
    }
}
