package co.edu.uco.FondaControl.api;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v10/roles")
public class RolController {

    @GetMapping
    public String consultar() {
        return "Consultar todos los roles";
    }

    @PostMapping
    public String registrar() {
        return "Registrar un nuevo rol";
    }

    @PutMapping("/{codigo}")
    public String modificar(@PathVariable UUID codigo) {
        return "Modificar el rol con código " + codigo;
    }

    @DeleteMapping("/{codigo}")
    public String eliminar(@PathVariable UUID codigo) {
        return "Eliminar el rol con código " + codigo;
    }
}
