package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v7/inventarios")
public class InventarioController {


    @GetMapping
    public String consultar() {
        return "Consulta todos los inventarios";
    }

    @PostMapping
    public String crear() {
        return "Registrar nuevo inventario";
    }

    @PutMapping
    public String modificar() {
        return "Modificar inventario existente";
    }

    @DeleteMapping
    public String eliminar() {
        return "Eliminar inventario por código";
    }
}
