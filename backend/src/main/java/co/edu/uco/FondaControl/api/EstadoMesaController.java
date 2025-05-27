package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/estado-mesas")
public class EstadoMesaController {

    @GetMapping
    public String consultar() {
        return "Consulta todos los estados de mesa";
    }

    @PostMapping
    public String registrar() {
        return "Registrar un nuevo estado de mesa";
    }

    @PutMapping
    public String modificar() {
        return "Modificar un estado de mesa";
    }

    @DeleteMapping
    public String eliminar() {
        return "Eliminar un estado de mesa por código";
    }
}
