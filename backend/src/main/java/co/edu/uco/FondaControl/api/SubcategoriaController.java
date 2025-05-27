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
@RequestMapping("/api/v12/subcategorias")
public class SubcategoriaController {

	@GetMapping
	public String consultar() {
		return "Consultar todas las subcategorías";
	}

	@PostMapping
	public String registrar() {
		return "Registrar una nueva subcategoría";
	}

	@PutMapping("/{codigo}")
	public String modificar(@PathVariable UUID codigo) {
		return "Modificar la subcategoría con código " + codigo;
	}

	@DeleteMapping("/{codigo}")
	public String eliminar(@PathVariable UUID codigo) {
		return "Eliminar la subcategoría con código " + codigo;
	}
}
