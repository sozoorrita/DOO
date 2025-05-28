package co.edu.uco.FondaControl.api;

import co.edu.uco.FondaControl.businesslogic.facade.IndicadorInventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v5/indicador-inventarios")
public class IndicadorInventarioController {

    @Autowired
    private IndicadorInventarioFacade facade;

    @GetMapping("/dummy")
    public List<IndicadorInventarioDTO> consultar(@RequestParam(required = false) UUID codigo) throws FondaControlException {
        IndicadorInventarioDTO filtro = IndicadorInventarioDTO.obtenerValorDefecto();
        if (!UtilUUID.esValorDefecto(codigo)) {
            filtro.setCodigo(codigo);
        }
        return facade.consultarIndicadorInventario(filtro);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar(@RequestBody IndicadorInventarioDTO dto) throws FondaControlException {
        facade.registrarIndicadorInventario(dto);
    }

    @PutMapping("/evaluar/{codigo}")
    public void evaluar(@PathVariable UUID codigo, @RequestBody IndicadorInventarioDTO dto) throws FondaControlException {
        facade.evaluarIndicadorInventario(codigo, dto);
    }

    @PutMapping("/configurar/{codigo}")
    public void configurar(@PathVariable UUID codigo, @RequestBody IndicadorInventarioDTO dto) throws FondaControlException {
        facade.configurarIndicadorInventario(codigo, dto);
    }
}
