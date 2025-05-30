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

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listarProductos() throws FondaControlException {
        List<ProductoDTO> lista = productoFacade.consultarProducto(new ProductoDTO());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable("id") UUID id)
            throws FondaControlException {
        ProductoDTO dto = new ProductoDTO();
        dto.setCodigoProducto(id);
        productoFacade.consultarProductoPorCodigo(dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO producto)
            throws FondaControlException {
        productoFacade.registrarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable("id") UUID id,
                                                          @RequestBody ProductoDTO producto)
            throws FondaControlException {
        producto.setCodigoProducto(id);
        productoFacade.modificarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProducto(@PathVariable("id") UUID id) throws FondaControlException {
        ProductoDTO dto = new ProductoDTO();
        dto.setCodigoProducto(id);
        productoFacade.eliminarProducto(dto);
    }
}
