package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.FondaControl.businesslogic.facade.DetalleVentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;

@RestController
@RequestMapping("/api/v1/detalle-ventas")
public class DetalleVentaController {

    private final DetalleVentaFacade detalleVentaFacade;

    public DetalleVentaController(DetalleVentaFacade detalleVentaFacade) {
        this.detalleVentaFacade = detalleVentaFacade;
    }

    @GetMapping("/dummy")
    public DetalleVentaDTO getDummy() {
        return new DetalleVentaDTO();
    }

    
    @GetMapping
    public ResponseEntity<List<DetalleVentaDTO>> consultar(
            @RequestBody(required = false) DetalleVentaDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = getDummy();
        }
        List<DetalleVentaDTO> lista = detalleVentaFacade.consultarDetalleVenta(filtro);
        return ResponseEntity.ok(lista);
    }

    
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody DetalleVentaDTO detalle)
            throws FondaControlException {
        detalleVentaFacade.registrarDetalleVenta(detalle);
        String mensaje = "El detalle de venta ha sido registrado exitosamente.";
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody DetalleVentaDTO detalle)
            throws FondaControlException {
        detalle.setCodigo(id);
        detalleVentaFacade.modificarDetalleVenta(detalle);
        String mensaje = "El detalle de venta con código " + id + " ha sido modificado exitosamente.";
        return ResponseEntity.ok(mensaje);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") UUID id)
            throws FondaControlException {
        
        DetalleVentaDTO filtro = new DetalleVentaDTO();
        filtro.setCodigo(id);
        var encontrados = detalleVentaFacade.consultarDetalleVenta(filtro);
        DetalleVentaDTO dto = encontrados.isEmpty() ? null : encontrados.get(0);

        DetalleVentaDTO aEliminar = new DetalleVentaDTO();
        aEliminar.setCodigo(id);
        detalleVentaFacade.eliminarDetalleVenta(aEliminar);

        String producto = (dto != null) ? dto.getNombreProducto() : "";
        String mensaje = "El detalle de venta" +
                (producto.isEmpty() ? "" : " [producto: " + producto + "]") +
                " ha sido eliminado exitosamente.";
        return ResponseEntity.ok(mensaje);
    }
}
