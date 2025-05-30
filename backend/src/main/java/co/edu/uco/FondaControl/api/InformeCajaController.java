package co.edu.uco.FondaControl.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/informe-caja")
public class InformeCajaController {

    private final InformeCajaFacade informeCajaFacade;

    public InformeCajaController(InformeCajaFacade informeCajaFacade) {
        this.informeCajaFacade = informeCajaFacade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InformeCajaDTO crearInformeCaja(@RequestBody InformeCajaDTO informeCajaDTO) throws FondaControlException {
        informeCajaFacade.crearInformeCaja(informeCajaDTO);
        return informeCajaDTO;
    }

    @GetMapping
    public List<InformeCajaDTO> listarInformes(
            @RequestBody(required = false) InformeCajaDTO filtro) throws FondaControlException {
        return informeCajaFacade.consultarInformeCaja(filtro);
    }

    @GetMapping("/{codigo}")
    public InformeCajaDTO obtenerInformePorCodigo(
            @PathVariable("codigo") UUID codigo) throws FondaControlException {
        InformeCajaDTO dto = new InformeCajaDTO();
        dto.setCodigo(codigo);
        informeCajaFacade.consultarInformeCajaPorCodigo(dto);
        return dto;
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarInforme(
            @PathVariable("codigo") UUID codigo) throws FondaControlException {
        InformeCajaDTO dto = new InformeCajaDTO();
        dto.setCodigo(codigo);
        informeCajaFacade.eliminarInformeCaja(dto);
    }

    @PutMapping("/consolidar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void consolidarVentas(
            @RequestBody InformeCajaDTO informeCajaDTO) throws FondaControlException {
        informeCajaFacade.consolidarVentasInformeCaja(informeCajaDTO);
    }
}
