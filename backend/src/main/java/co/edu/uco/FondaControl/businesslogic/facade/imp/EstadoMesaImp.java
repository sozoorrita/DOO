package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.EstadoMesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.EstadoMesaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.dto.EstadoMesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.EstadoMesaFacade;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstadoMesaImp implements EstadoMesaFacade {
    private final EstadoMesaBusinessLogic logic;

    public EstadoMesaImp(DAOFactory dao) {
        this.logic = new EstadoMesaImpl(dao);
    }

    @Override
    public void evaluarEstadoMesa(UUID codigo, EstadoMesaDTO estadoMesa) throws FondaControlException {
        EstadoMesaDomain domain = EstadoMesaDTOAssembler.getInstance().toDomain(estadoMesa);
        logic.evaluarEstadoMesa(codigo, domain);
    }

    @Override
    public void registrarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        EstadoMesaDomain domain = EstadoMesaDTOAssembler.getInstance().toDomain(dto);
        logic.registrarEstadoMesa(domain);
    }

    @Override
    public List<EstadoMesaDTO> consultarEstadoMesa(UUID codigo) throws FondaControlException {
        List<EstadoMesaDomain> domains = logic.consultarEstadoMesa(codigo);
        return EstadoMesaDTOAssembler.getInstance().toDtoList(domains);
    }

    @Override
    public void modificarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        logic.modificarEstadoMesa(dto);
    }

    @Override
    public void eliminarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        UUID codigo = EstadoMesaDTOAssembler.getInstance().toDomain(dto).getCodigo();
        logic.eliminarEstadoMesa(codigo);
    }
}