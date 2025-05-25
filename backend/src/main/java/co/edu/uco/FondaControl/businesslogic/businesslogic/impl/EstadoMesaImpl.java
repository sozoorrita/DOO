package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.EstadoMesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.EstadoMesaDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.EstadoMesaEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EstadoMesaImpl implements EstadoMesaBusinessLogic {

    private final DAOFactory daoFactory;
    private final EstadoMesaDAO dao;

    public EstadoMesaImpl(final DAOFactory daoFactory) throws DataFondaControlException {
        this.daoFactory = daoFactory;
        this.dao = daoFactory.getEstadoMesaDAO();
    }

    @Override
    public void evaluarEstadoMesa(UUID codigo, EstadoMesaDomain domain) throws FondaControlException {
        validarCodigo(codigo);
        validarDomain(domain);
        dao.update(codigo, toEntity(domain));
    }

    @Override
    public void configurarEstadoMesa(UUID codigo, EstadoMesaDomain domain) throws FondaControlException {
        validarCodigo(codigo);
        validarDomain(domain);
        dao.update(codigo, toEntity(domain));
    }

    @Override
    public void registrarEstadoMesa(EstadoMesaDomain domain) throws FondaControlException {
        validarDomain(domain);
        dao.create(toEntity(domain));
    }

    @Override
    public List<EstadoMesaDomain> consultarEstadoMesa(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            return dao.listAll().stream().map(this::toDomain).collect(Collectors.toList());
        }

        return dao.listByCodigo(codigo).stream().map(this::toDomain).collect(Collectors.toList());
    }



    private void validarDomain(EstadoMesaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("El estado de mesa no puede ser nulo.");
        }
    }

    private void validarCodigo(UUID codigo) {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del estado de mesa no puede ser nulo.");
        }
    }

    private EstadoMesaEntity toEntity(EstadoMesaDomain domain) {
        return new EstadoMesaEntity(domain.getCodigo(), domain.getNombre());
    }

    private EstadoMesaDomain toDomain(EstadoMesaEntity entity) {
        return EstadoMesaDomain.crear(entity.getCodigo(), entity.getNombre());
    }
}
