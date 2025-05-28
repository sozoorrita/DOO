package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.EstadoMesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.EstadoMesaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.dto.EstadoMesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.EstadoMesaFacade;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoMesaImp implements EstadoMesaFacade {
    private final EstadoMesaBusinessLogic logic;

    @Autowired
    public EstadoMesaImp(DAOFactory dao) {
        this.logic = new EstadoMesaImpl(dao);
    }

    @Override
    public void registrarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        EstadoMesaDomain domain = EstadoMesaDTOAssembler.getInstancia().toDomain(dto);
        logic.registrarEstadoMesa(domain);
    }

    @Override
    public void modificarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        EstadoMesaDomain domain = EstadoMesaDTOAssembler.getInstancia().toDomain(dto);
        logic.modificarEstadoMesa(domain.getCodigo(), domain);
    }

    @Override
    public void eliminarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        logic.eliminarEstadoMesa(EstadoMesaDTOAssembler.getInstancia().toDomain(dto).getCodigo());
    }

    @Override
    public List<EstadoMesaDTO> consultarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        var filter = EstadoMesaDTOAssembler.getInstancia().toDomain(dto);
        var domains = logic.consultarEstadoMesa(filter);
        return EstadoMesaDTOAssembler.getInstancia().toDtoList(domains);
    }
}