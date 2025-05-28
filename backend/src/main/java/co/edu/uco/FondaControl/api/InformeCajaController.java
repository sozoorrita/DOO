package co.edu.uco.FondaControl.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

@RestController
@RequestMapping("/api/v6/informe-caja")
public class InformeCajaController {

    private final InformeCajaFacade informeCajaFacade;

    public InformeCajaController(InformeCajaFacade informeCajaFacade) {
        this.informeCajaFacade = informeCajaFacade;
    }

    @PutMapping("/consolidar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void consolidarVentas(@RequestBody InformeCajaDTO informeCajaDTO) throws FondaControlException {
        informeCajaFacade.consolidarVentasInformeCaja(informeCajaDTO);
    }
}
