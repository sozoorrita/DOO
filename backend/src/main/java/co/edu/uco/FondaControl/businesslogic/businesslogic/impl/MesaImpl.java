package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.MesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.entity.mesa.MesaDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.MesaEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MesaImpl implements MesaBusinessLogic {

    private final DAOFactory daoFactory;
    private final MesaDAO mesaDAO;

    public MesaImpl(final DAOFactory daoFactory) throws DataFondaControlException {
        this.daoFactory = daoFactory;
        this.mesaDAO = daoFactory.getMesaDAO();
    }

    @Override
    public void evaluarMesa(UUID codigo, MesaDomain domain) throws FondaControlException {
        validarCodigo(codigo);
        validarDomain(domain);
        mesaDAO.update(codigo, toEntity(domain));
    }

    @Override
    public void configurarMesa(UUID codigo, MesaDomain domain) throws FondaControlException {
        validarCodigo(codigo);
        validarDomain(domain);
        mesaDAO.update(codigo, toEntity(domain));
    }

    @Override
    public void registrarMesa(MesaDomain domain) throws FondaControlException {
        validarDomain(domain);
        mesaDAO.create(toEntity(domain));
    }

    @Override
    public List<MesaDomain> consultarMesa(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            return mesaDAO.listAll().stream().map(this::toDomain).collect(Collectors.toList());
        }

        return mesaDAO.listByCodigo(codigo).stream().map(this::toDomain).collect(Collectors.toList());
    }



    private void validarDomain(MesaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }
    }

    private void validarCodigo(UUID codigo) {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo.");
        }
    }


    private MesaEntity toEntity(MesaDomain domain) {
        return new MesaEntity(
                domain.getCodigo(),
                domain.getIdentificador(),
                domain.getEstado().getCodigo()
        );
    }

    private MesaDomain toDomain(MesaEntity entity) {
        EstadoMesaDomain estado = EstadoMesaDomain.crear(entity.getCodigoEstadoMesa(), "");
        return MesaDomain.crear(entity.getCodigo(), entity.getNombre(), estado);
    }
}
