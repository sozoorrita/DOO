package co.edu.uco.FondaControl.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.FondaControl.businesslogic.facade.IndicadorInventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

@RestController
@RequestMapping("/api/v1/indicador-inventarios")
public class IndicadorInventarioController {

    private final IndicadorInventarioFacade facade;

    public IndicadorInventarioController(IndicadorInventarioFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public List<IndicadorInventarioDTO> consultar(@RequestParam(required = false) UUID codigo)
            throws FondaControlException {
        IndicadorInventarioDTO filtro = IndicadorInventarioDTO.obtenerValorDefecto();
        if (codigo != null && !UtilUUID.esValorDefecto(codigo)) {
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void evaluar(@PathVariable UUID codigo,
                        @RequestBody IndicadorInventarioDTO dto) throws FondaControlException {
        facade.evaluarIndicadorInventario(codigo, dto);
    }

    @PutMapping("/configurar/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void configurar(@PathVariable UUID codigo,
                           @RequestBody IndicadorInventarioDTO dto) throws FondaControlException {
        facade.configurarIndicadorInventario(codigo, dto);
    }

    // Consulta de existencia (void, según tu interfaz)
    @GetMapping("/{codigo}/exists")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void consultarPorCodigo(@PathVariable UUID codigo) throws FondaControlException {
        facade.consultarIndicadorInventarioPorCodigo(codigo);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID codigo,
                         @RequestBody IndicadorInventarioDTO dto) throws FondaControlException {
        facade.eliminarIndicadorInventario(codigo, dto);
    }
}
