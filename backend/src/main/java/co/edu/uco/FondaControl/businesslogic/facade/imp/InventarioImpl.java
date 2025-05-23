package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.businesslogic.facade.InventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.dto.InventarioDTO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.util.UUID;


public class InventarioImpl implements InventarioFacade {

    private final InventarioBusinessLogic businessLogic;

    public InventarioImpl(InventarioBusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    @Override
    public void actualizarCantidadEnInventario(InventarioDTO inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        InventarioDomain domain = mapToDomain(inventario);
        businessLogic.actualizarCantidadEnInventario(domain);
    }

    @Override
    public void consultarCantidadInventario(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }
        businessLogic.consultarCantidadInventario(codigo);
    }

    @Override
    public void gestionarInventarioManualmente(InventarioDTO inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        InventarioDomain domain = mapToDomain(inventario);
        businessLogic.gestionarInventarioManualmente(domain);
    }

    private InventarioDomain mapToDomain(InventarioDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return null;
        }
        IndicadorInventarioDomain indicadorDomain = new IndicadorInventarioDomain(
                dto.getCodigoIndicador(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombreIndicador())
        );
        return new InventarioDomain(
                dto.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombreProducto()),
                dto.getCantidad(),
                indicadorDomain
        );
    }

    private InventarioDTO mapToDTO(InventarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return null;
        }
        return new InventarioDTO(
                domain.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombreProducto()),
                domain.getCantidad(),
                domain.getCodigoIndicador(),
                UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombreIndicador())
        );
    }
}
