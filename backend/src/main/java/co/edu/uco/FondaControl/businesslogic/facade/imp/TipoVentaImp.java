package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.uco.FondaControl.businesslogic.businesslogic.TipoVentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.TipoVentaImpl;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.TipoVentaFacade;
import co.edu.uco.FondaControl.dto.TipoVentaDTO;

@Service
public class TipoVentaImp implements TipoVentaFacade {

    private final TipoVentaBusinessLogic businessLogic;

    public TipoVentaImp(DAOFactory daoFactory) {
        this.businessLogic = new TipoVentaImpl(daoFactory);
    }

    @Override
    public void registrarTipoVenta(TipoVentaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilTexto.getInstancia().esNula(dto.getNombre())) {
            throw new IllegalArgumentException(
                "El tipo de venta a registrar no puede ser nulo y debe contener un nombre."
            );
        }
        businessLogic.registrarTipoVenta(mapToDomain(dto));
    }

    @Override
    public void modificarTipoVenta(TipoVentaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())
                || UtilTexto.getInstancia().esNula(dto.getNombre())) {
            throw new IllegalArgumentException(
                "El tipo de venta a modificar no puede ser nulo, debe contener un código y un nombre."
            );
        }
        businessLogic.modificarTipoVenta(mapToDomain(dto));
    }

    @Override
    public void eliminarTipoVenta(TipoVentaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())) {
            throw new IllegalArgumentException(
                "El tipo de venta a eliminar no puede ser nulo y debe contener un código."
            );
        }
        businessLogic.eliminarTipoVenta(mapToDomain(dto));
    }

    @Override
    public List<TipoVentaDTO> consultarTipoVenta(TipoVentaDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigo())) {
            throw new IllegalArgumentException(
                "El filtro no puede ser nulo y debe contener un código."
            );
        }
        return businessLogic
            .consultarTipoVenta(filtro.getCodigo())
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    private TipoVentaDomain mapToDomain(TipoVentaDTO dto) {
        return new TipoVentaDomain(
            dto.getCodigo(),
            UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(dto.getNombre())
        );
    }

    private TipoVentaDTO mapToDTO(TipoVentaDomain domain) {
        return new TipoVentaDTO.Builder()
            .codigo(domain.getCodigo())
            .nombre(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre()))
            .crear();
    }
}
