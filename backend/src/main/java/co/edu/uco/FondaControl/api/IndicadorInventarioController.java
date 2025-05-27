package co.edu.uco.FondaControl.api;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/indicador-inventarios")
public class IndicadorInventarioController {


    @GetMapping
    public String consultar(){
        return "consulta todos los indicadores de inventario";
    }

    @PostMapping
    public String crear() {
        return "Registra un indicador de inventario";
    }

    @PutMapping
    public String modificar() {
        return "Modificar indicador de inventario";
    }

    @DeleteMapping
    public String eliminar() {
        return "Eliminar de forma definitiva un indicador de inventario";
    }


}




