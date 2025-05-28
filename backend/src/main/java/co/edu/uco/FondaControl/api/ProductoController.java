package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.ProductoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.ProductoDTO;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoFacade productoFacade;

    public ProductoController(ProductoFacade productoFacade) {
        this.productoFacade = productoFacade;
    }

    
    @GetMapping("/dummy")
    public ProductoDTO dummy() {
        return new ProductoDTO();
    }

    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> consultar(
            @RequestBody(required = false) ProductoDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = dummy();
        }
        List<ProductoDTO> lista = productoFacade.consultarProducto(filtro);
        return ResponseEntity.ok(lista);
    }

    
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody ProductoDTO producto)
            throws FondaControlException {
        productoFacade.registrarProducto(producto);
        String msg = "El producto \"" + producto.getNombre() + "\" ha sido creado exitosamente.";
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody ProductoDTO producto)
            throws FondaControlException {
        producto.setCodigoProducto(id);
        productoFacade.modificarProducto(producto);
        String msg = "El producto \"" + producto.getNombre() + "\" ha sido modificado exitosamente.";
        return ResponseEntity.ok(msg);
    }

  
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        ProductoDTO dto = new ProductoDTO();
        dto.setCodigoProducto(id);
        productoFacade.eliminarProducto(dto);
    }
}
