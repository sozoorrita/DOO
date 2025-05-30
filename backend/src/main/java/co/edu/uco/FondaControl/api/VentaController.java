package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.VentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.VentaDTO;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    private final VentaFacade ventaFacade;

    public VentaController(VentaFacade ventaFacade) {
        this.ventaFacade = ventaFacade;
    }

    @GetMapping
    public ResponseEntity<List<VentaDTO>> listar(
            @RequestBody(required = false) VentaDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = new VentaDTO();
        }
        List<VentaDTO> lista = ventaFacade.consultarVenta(filtro);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerPorId(@PathVariable("id") UUID id)
            throws FondaControlException {
        VentaDTO dto = new VentaDTO();
        dto.setCodigo(id);
        ventaFacade.consultarVentaPorCodigo(dto);
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<VentaDTO> crear(@RequestBody VentaDTO venta) throws FondaControlException {
        ventaFacade.registrarVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(venta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizar(@PathVariable("id") UUID id,
                                               @RequestBody VentaDTO venta) throws FondaControlException {
        venta.setCodigo(id);
        ventaFacade.modificarVenta(venta);
        return ResponseEntity.ok(venta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        VentaDTO dto = new VentaDTO();
        dto.setCodigo(id);
        ventaFacade.eliminarVenta(dto);
    }
}
