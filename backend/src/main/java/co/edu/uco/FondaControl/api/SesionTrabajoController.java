package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v11/sesion-trabajo")
public class SesionTrabajoController {

    @PostMapping("/iniciar")
    public String iniciarSesionTrabajo() {
        return "Iniciar sesión de trabajo";
    }

    @PutMapping("/cerrar")
    public String cerrarSesionTrabajo() {
        return "Cerrar sesión de trabajo";
    }
}
