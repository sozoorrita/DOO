package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.CategoriaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.CategoriaDTO;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CategoriaController {

    private final CategoriaFacade categoriaFacade;

    public CategoriaController(CategoriaFacade categoriaFacade) {
        this.categoriaFacade = categoriaFacade;
    }

    @GetMapping("/dummy")
    public CategoriaDTO dummy() {
        return new CategoriaDTO();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> consultar() throws FondaControlException {
        CategoriaDTO filtro = new CategoriaDTO(); // sin filtros
        List<CategoriaDTO> lista = categoriaFacade.consultarCategoria(filtro);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody CategoriaDTO categoria)
            throws FondaControlException {
        categoriaFacade.registrarCategoria(categoria);
        String mensaje = "La categoría \"" + categoria.getNombre() + "\" ha sido creada exitosamente.";
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody CategoriaDTO categoria)
            throws FondaControlException {
        categoria.setCodigo(id);
        categoriaFacade.modificarCategoria(categoria);
        String mensaje = "La categoría \"" + categoria.getNombre() + "\" ha sido modificada exitosamente.";
        return ResponseEntity.ok(mensaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") UUID id) {
        try {
            CategoriaDTO filtro = new CategoriaDTO();
            filtro.setCodigo(id);
            List<CategoriaDTO> encontrados = categoriaFacade.consultarCategoria(filtro);
            if (encontrados == null || encontrados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe una categoría con el código proporcionado: " + id);
            }

            String nombre = encontrados.get(0).getNombre();

            CategoriaDTO aEliminar = new CategoriaDTO();
            aEliminar.setCodigo(id);
            categoriaFacade.eliminarCategoria(aEliminar);

            String mensaje = "La categoría \"" + nombre + "\" ha sido eliminada exitosamente.";
            return ResponseEntity.ok(mensaje);

        } catch (FondaControlException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al intentar eliminar la categoría.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> consultarPorCodigo(@PathVariable("id") UUID id)
            throws FondaControlException {
        CategoriaDTO filtro = new CategoriaDTO();
        filtro.setCodigo(id);
        CategoriaDTO resultado = categoriaFacade.consultarCategoriaPorCodigo(filtro);
        return ResponseEntity.ok(resultado);
    }
}
