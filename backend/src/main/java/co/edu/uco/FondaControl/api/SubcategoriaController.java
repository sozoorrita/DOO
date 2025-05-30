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
@RequestMapping("/api/v1/subcategorias")
public class SubcategoriaController {

    private final SubcategoriaFacade subcategoriaFacade;

    public SubcategoriaController(SubcategoriaFacade subcategoriaFacade) {
        this.subcategoriaFacade = subcategoriaFacade;
    }


    @GetMapping
    public ResponseEntity<List<SubcategoriaDTO>> listar(@RequestBody(required = false) SubcategoriaDTO filtro)
            throws FondaControlException {
        if (filtro == null) {
            filtro = new SubcategoriaDTO();
        }
        List<SubcategoriaDTO> lista = subcategoriaFacade.consultarSubcategoria(filtro);
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SubcategoriaDTO> obtenerPorId(@PathVariable("id") UUID id)
            throws FondaControlException {
        SubcategoriaDTO filtro = new SubcategoriaDTO();
        filtro.setCodigo(id);
        subcategoriaFacade.consultarSubcategoriaPorCodigo(filtro);
        return ResponseEntity.ok(filtro);
    }


    @PostMapping
    public ResponseEntity<SubcategoriaDTO> crear(@RequestBody SubcategoriaDTO subcategoria)
            throws FondaControlException {
        subcategoriaFacade.registrarSubcategoria(subcategoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(subcategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoriaDTO> actualizar(@PathVariable("id") UUID id,
                                                      @RequestBody SubcategoriaDTO subcategoria)
            throws FondaControlException {
        subcategoria.setCodigo(id);
        subcategoriaFacade.modificarSubcategoria(subcategoria);
        return ResponseEntity.ok(subcategoria);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        SubcategoriaDTO dto = new SubcategoriaDTO();
        dto.setCodigo(id);
        subcategoriaFacade.eliminarSubcategoria(dto);
    }
}
