package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.FormaPagoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.FormaPagoDTO;

@RestController
@RequestMapping("/api/v1/formas-pago")
public class FormaPagoController {

    private final FormaPagoFacade formaPagoFacade;

    public FormaPagoController(FormaPagoFacade formaPagoFacade) {
        this.formaPagoFacade = formaPagoFacade;
    }

   
    @GetMapping("/dummy")
    public FormaPagoDTO getDummy() {
        return new FormaPagoDTO();
    }

    
    @GetMapping
    public ResponseEntity<List<FormaPagoDTO>> consultar(
            @RequestBody(required = false) FormaPagoDTO filtro) throws FondaControlException {
        if (filtro == null) {
            filtro = getDummy();
        }
        List<FormaPagoDTO> lista = formaPagoFacade.consultarFormaPago(filtro);
        return ResponseEntity.ok(lista);
    }

   
    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody FormaPagoDTO formaPago)
            throws FondaControlException {
        formaPagoFacade.registrarFormaPago(formaPago);
        String mensaje = "La forma de pago \"" + formaPago.getNombre() + "\" ha sido creada exitosamente.";
        return new ResponseEntity<>(mensaje, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<String> modificar(@PathVariable("id") UUID id,
                                            @RequestBody FormaPagoDTO formaPago)
            throws FondaControlException {
        formaPago.setCodigo(id);
        formaPagoFacade.modificarFormaPago(formaPago);
        String mensaje = "La forma de pago \"" + formaPago.getNombre() + "\" ha sido modificada exitosamente.";
        return ResponseEntity.ok(mensaje);
    }

    
    public ResponseEntity<String> eliminar(@PathVariable("id") UUID id)
            throws FondaControlException {
        FormaPagoDTO dto = new FormaPagoDTO();
        dto.setCodigo(id);
        formaPagoFacade.eliminarFormaPago(dto);
        String mensaje = "La forma de pago con código " + id + " ha sido eliminada exitosamente.";
        return ResponseEntity.ok(mensaje);
    }
}
