package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.TipoVentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.TipoVentaDTO;

@RestController
@RequestMapping("/api/v13/tipos-venta")
public class TipoVentaController {

    private final TipoVentaFacade tipoVentaFacade;

    public TipoVentaController(TipoVentaFacade tipoVentaFacade) {
        this.tipoVentaFacade = tipoVentaFacade;
    }

    @GetMapping("/dummy")
    public TipoVentaDTO dummy() {
        return new TipoVentaDTO.Builder().crear();
    }

    @GetMapping
    public ResponseEntity<List<TipoVentaDTO>> consultar(
            @RequestBody(required = false) TipoVentaDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = dummy();
        }
        List<TipoVentaDTO> lista = tipoVentaFacade.consultarTipoVenta(filtro);
        return ResponseEntity.ok(lista);
    }

   @PostMapping
    public ResponseEntity<String> registrar(@RequestBody TipoVentaDTO dto)
            throws FondaControlException {
        tipoVentaFacade.registrarTipoVenta(dto);
        String msg = "El tipo de venta \"" + dto.getNombre() + "\" ha sido registrado exitosamente.";
        return ResponseEntity.status(HttpStatus.CREATED).body(msg);
    }

   @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody TipoVentaDTO dto)
            throws FondaControlException {
        dto.setCodigo(id);
        tipoVentaFacade.modificarTipoVenta(dto);
        String msg = "El tipo de venta \"" + dto.getNombre() + "\" ha sido modificado exitosamente.";
        return ResponseEntity.ok(msg);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") UUID id) throws FondaControlException {
        TipoVentaDTO dto = new TipoVentaDTO.Builder().codigo(id).crear();
        tipoVentaFacade.eliminarTipoVenta(dto);
    }
}
