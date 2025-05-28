package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.DetalleVentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.DetalleVentaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.dto.DetalleVentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.DetalleVentaFacade;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaImp implements DetalleVentaFacade {
    private final DetalleVentaBusinessLogic logic;

    @Autowired
    public DetalleVentaImp(DAOFactory dao) {
        this.logic = new DetalleVentaImpl(dao);
    }

    @Override
    public void registrarDetalleVenta(DetalleVentaDTO dto) throws FondaControlException {
        DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(dto);
        logic.registrarDetalleVenta(domain);
    }

    @Override
    public void modificarDetalleVenta(DetalleVentaDTO dto) throws FondaControlException {
        DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(dto);
        logic.modificarDetalleVenta(domain.getCodigoDetalleVenta(), domain);
    }

    @Override
    public void eliminarDetalleVenta(DetalleVentaDTO dto) throws FondaControlException {
        logic.eliminarDetalleVenta(DetalleVentaDTOAssembler.getInstancia().toDomain(dto).getCodigoDetalleVenta());
    }

    @Override
    public List<DetalleVentaDTO> consultarDetalleVenta(DetalleVentaDTO dto) throws FondaControlException {
        var filter = DetalleVentaDTOAssembler.getInstancia().toDomain(dto);
        var domains = logic.consultarDetalleVenta(filter);
        return DetalleVentaDTOAssembler.getInstancia().toDtoList(domains);
    }
}