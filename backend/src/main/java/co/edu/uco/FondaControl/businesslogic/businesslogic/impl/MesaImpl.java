package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.MesaBusinessLogic;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.Entity.MesaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.MesaEntity;

import java.util.List;
import java.util.UUID;

public final class MesaImpl implements MesaBusinessLogic {

    private final DAOFactory daoFactory;

    public MesaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void evaluarMesa(final UUID codigo, final MesaDomain domain) throws FondaControlException {
        validarCodigo(codigo);
        validarDomain(domain);
        validarIntegridadIdentificador(domain.getIdentificador());
        daoFactory.getMesaDAO().update(codigo, MesaEntityAssembler.getInstance().toEntity(domain));
    }

    @Override
    public void configurarMesa(final UUID codigo, final MesaDomain domain) throws FondaControlException {
        validarCodigo(codigo);
        validarDomain(domain);
        final var entity = MesaEntityAssembler.getInstance().toEntity(domain);
        daoFactory.getMesaDAO().update(codigo, entity);
    }

    @Override
    public void registrarMesa(final MesaDomain domain) throws FondaControlException {
        validarDomain(domain);
        validarIntegridadIdentificador(domain.getIdentificador());
        validarNoExistaMesaConMismoIdentificador(domain.getIdentificador());

        final var nuevoCodigo = generarNuevoCodigoMesa();
        final var nuevaMesa = MesaDomain.crear(nuevoCodigo, domain.getIdentificador(), domain.getEstado());

        final var entity = MesaEntityAssembler.getInstance().toEntity(nuevaMesa);
        daoFactory.getMesaDAO().create(entity);
    }

    @Override
    public List<MesaDomain> consultarMesa(final UUID codigo) throws FondaControlException {
        final var dao = daoFactory.getMesaDAO();

        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return MesaEntityAssembler.getInstance().toDomainList(dao.listAll());
        }

        return MesaEntityAssembler.getInstance().toDomainList(dao.listByCodigo(codigo));
    }

    private void validarDomain(final MesaDomain domain) {
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }
    }

    private void validarCodigo(final UUID codigo) {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo ni por defecto.");
        }
    }

    private void validarIntegridadIdentificador(final String identificador) throws BusinessLogicFondaControlException {
        if (UtilTexto.getInstancia().esNula(identificador)) {
            throw BusinessLogicFondaControlException.reportar("El identificador de la mesa es obligatorio.");
        }
        if (identificador.length() > 50) {
            throw BusinessLogicFondaControlException.reportar("El identificador de la mesa no puede tener más de 50 caracteres.");
        }
    }

    private void validarNoExistaMesaConMismoIdentificador(final String identificador) throws BusinessLogicFondaControlException, DataFondaControlException {
        final var filtro = new MesaEntity();
        filtro.setNombre(identificador);

        final var resultado = daoFactory.getMesaDAO().listByFilter(filtro);
        if (!resultado.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar("Ya existe una mesa con el mismo identificador. No es posible registrarla nuevamente.");
        }
    }

    private UUID generarNuevoCodigoMesa() throws DataFondaControlException {
        UUID nuevoCodigo;
        boolean yaExiste;

        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
            final var resultado = daoFactory.getMesaDAO().listByCodigo(nuevoCodigo);
            yaExiste = !resultado.isEmpty();
        } while (yaExiste);

        return nuevoCodigo;
    }
}
