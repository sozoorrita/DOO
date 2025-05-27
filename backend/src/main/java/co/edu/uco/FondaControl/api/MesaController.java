package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v5/mesas")
public class MesaController {

    @GetMapping
    public String consultar() {
        return "Consulta todas las mesas";
    }

    @PostMapping
    public String registrar() {
        return "Registrar una nueva mesa";
    }

    @PutMapping
    public String modificar() {
        return "Modificar una mesa";
    }

    @DeleteMapping
    public String eliminar() {
        return "Eliminar una mesa por código";
    }
}
