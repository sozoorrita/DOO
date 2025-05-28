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

    @GetMapping("/dummy")
    public VentaDTO dummy() {
        return new VentaDTO();
    }

    @GetMapping
    public ResponseEntity<List<VentaDTO>> consultar(@RequestBody(required = false) VentaDTO filtro)
            throws FondaControlException {
        if (filtro == null) {
            filtro = dummy();
        }
        List<VentaDTO> lista = ventaFacade.consultarVenta(filtro);
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody VentaDTO venta) throws FondaControlException {
        ventaFacade.registrarVenta(venta);
        String msg = "La venta ha sido registrada exitosamente.";
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody VentaDTO venta) throws FondaControlException {
        venta.setCodigo(id);
        ventaFacade.modificarVenta(venta);
        String msg = "La venta con código " + id + " ha sido modificada exitosamente.";
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        VentaDTO dto = new VentaDTO();
        dto.setCodigo(id);
        ventaFacade.eliminarVenta(dto);
    }
}
