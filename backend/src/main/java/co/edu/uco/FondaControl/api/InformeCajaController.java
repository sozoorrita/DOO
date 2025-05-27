package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v6/informe-caja")
public class InformeCajaController {

    @PutMapping("/consolidar")
    public String consolidarVentasInformeCaja() {
        return "Consolidar ventas del informe de caja";
    }
}
