package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.EstadoMesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.EstadoMesaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.EstadoMesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EstadoMesaImp implements EstadoMesaFacade {

    private final DAOFactory daoFactory;
    private final EstadoMesaBusinessLogic businessLogic;

    public EstadoMesaImp() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new EstadoMesaImpl(daoFactory);
    }

    @Override
    public void evaluarEstadoMesa(UUID codigo, EstadoMesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        businessLogic.evaluarEstadoMesa(codigo, mapToDomain(dto));
    }

    @Override
    public void configurarEstadoMesa(UUID codigo, EstadoMesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        businessLogic.configurarEstadoMesa(codigo, mapToDomain(dto));
    }

    @Override
    public void registrarEstadoMesa(EstadoMesaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El estado de mesa no puede ser nulo.");
        }
        businessLogic.registrarEstadoMesa(mapToDomain(dto));
    }

    @Override
    public List<EstadoMesaDTO> consultarEstadoMesa(UUID codigo) throws FondaControlException {
        return businessLogic.consultarEstadoMesa(codigo).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ---------- Mapeo ----------

    private EstadoMesaDomain mapToDomain(EstadoMesaDTO dto) {
        return EstadoMesaDomain.crear(
                dto.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombre())
        );
    }

    private EstadoMesaDTO mapToDTO(EstadoMesaDomain domain) {
        return new EstadoMesaDTO(
                domain.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre())
        );
    }

    // ---------- Validaciones ----------

    private void validarEntrada(UUID codigo, EstadoMesaDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y el estado de mesa no pueden ser nulos.");
        }
    }
}
