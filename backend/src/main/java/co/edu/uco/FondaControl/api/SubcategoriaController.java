package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.SubcategoriaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.SubcategoriaDTO;

@RestController
@RequestMapping("/api/v12/subcategorias")
public class SubcategoriaController {

    private final SubcategoriaFacade subcategoriaFacade;

    public SubcategoriaController(SubcategoriaFacade subcategoriaFacade) {
        this.subcategoriaFacade = subcategoriaFacade;
    }

    
    @GetMapping("/dummy")
    public SubcategoriaDTO dummy() {
        return new SubcategoriaDTO();
    }

    
    @GetMapping
    public ResponseEntity<List<SubcategoriaDTO>> consultar(
            @RequestBody(required = false) SubcategoriaDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = dummy();
        }
        List<SubcategoriaDTO> lista = subcategoriaFacade.consultarSubcategoria(filtro);
        return ResponseEntity.ok(lista);
    }

    
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody SubcategoriaDTO subcategoria)
            throws FondaControlException {
        subcategoriaFacade.registrarSubcategoria(subcategoria);
        String msg = "La subcategoría \"" + subcategoria.getNombre() + "\" ha sido registrada exitosamente.";
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody SubcategoriaDTO subcategoria)
            throws FondaControlException {
        subcategoria.setCodigo(id);
        subcategoriaFacade.modificarSubcategoria(subcategoria);
        String msg = "La subcategoría \"" + subcategoria.getNombre() + "\" ha sido modificada exitosamente.";
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        SubcategoriaDTO dto = new SubcategoriaDTO();
        dto.setCodigo(id);
        subcategoriaFacade.eliminarSubcategoria(dto);
    }
}
