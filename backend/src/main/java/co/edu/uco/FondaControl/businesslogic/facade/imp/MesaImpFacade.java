package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.MesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.MesaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.MesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.MesaDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MesaImpFacade implements MesaFacade {

    private final DAOFactory daoFactory;
    private final MesaBusinessLogic businessLogic;

    public MesaImpFacade() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new MesaImpl(daoFactory);
    }

    @Override
    public void evaluarMesa(UUID codigo, MesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        businessLogic.evaluarMesa(codigo, mapToDomain(dto));
    }

    @Override
    public void configurarMesa(UUID codigo, MesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        businessLogic.configurarMesa(codigo, mapToDomain(dto));
    }

    @Override
    public void registrarMesa(MesaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }
        businessLogic.registrarMesa(mapToDomain(dto));
    }

    @Override
    public List<MesaDTO> consultarMesa(UUID codigo) throws FondaControlException {
        return businessLogic.consultarMesa(codigo).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    private MesaDomain mapToDomain(MesaDTO dto) {
        EstadoMesaDomain estado = EstadoMesaDomain.crear(
                dto.getCodigoEstadoMesa(),
                ""
        );

        return MesaDomain.crear(
                dto.getCodigo(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombre()),
                estado
        );
    }

    private MesaDTO mapToDTO(MesaDomain domain) {
        return new MesaDTO(
                domain.getCodigo(),
                domain.getIdentificador(),
                domain.getEstado().getCodigo()
        );
    }



    private void validarEntrada(UUID codigo, MesaDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y la mesa no pueden ser nulos.");
        }
    }
}
