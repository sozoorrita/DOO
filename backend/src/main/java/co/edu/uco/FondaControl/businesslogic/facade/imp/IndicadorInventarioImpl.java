package co.edu.uco.FondaControl.businesslogic.facade.imp;


import co.edu.uco.FondaControl.businesslogic.businesslogic.IndicadorInventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.IndicadorInventarioBusinessLogicImpl;
import co.edu.uco.FondaControl.businesslogic.facade.IndicadorInventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class IndicadorInventarioImpl implements IndicadorInventarioFacade {

    private final DAOFactory daoFactory;
    private final IndicadorInventarioBusinessLogic businessLogic;

    public IndicadorInventarioImpl() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new IndicadorInventarioBusinessLogicImpl(daoFactory);
    }

    @Override
    public void evaluarIndicadorInventario(UUID codigo, IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        businessLogic.evaluarIndicadorInventario(codigo, mapToDomain(dto));
    }

    @Override
    public void configurarIndicadorInventario(UUID codigo, IndicadorInventarioDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        businessLogic.configurarIndicadorInventario(codigo, mapToDomain(dto));
    }

    @Override
    public void registrarIndicadorInventario(IndicadorInventarioDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El indicador no puede ser nulo.");
        }
        businessLogic.registrarIndicadorInventario(mapToDomain(dto));
    }

    @Override
    public List<IndicadorInventarioDTO> consultarIndicadorInventario(IndicadorInventarioDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigo())) {
            throw new IllegalArgumentException("El filtro no puede ser nulo y debe contener un código.");
        }

        return businessLogic.consultarIndicadorInventario(filtro.getCodigo())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private void validarEntrada(UUID codigo, IndicadorInventarioDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y el indicador no pueden ser nulos.");
        }
    }

    private IndicadorInventarioDomain mapToDomain(IndicadorInventarioDTO dto) {
        return new IndicadorInventarioDomain(dto.getCodigo(), UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombre()));
    }

    private IndicadorInventarioDTO mapToDTO(IndicadorInventarioDomain domain) {
        return new IndicadorInventarioDTO(domain.getCodigo(), UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()));
    }
}
