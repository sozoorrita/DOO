package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.MesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.entity.MesaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
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
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }
        if (UtilObjeto.esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo ni por defecto.");
        }

        String id = domain.getIdentificador();
        if (UtilTexto.esNula(id) || id.length() > 10) {
            throw BusinessLogicFondaControlException.reportar(
                    "El identificador de la mesa es obligatorio y no puede exceder 10 caracteres.");
        }

        EstadoMesaDomain estado = domain.getEstado();
        if (UtilObjeto.esNulo(estado)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El estado de la mesa es obligatorio.");
        }

        MesaEntity entity = MesaEntityAssembler.getInstance().toEntity(domain);
        daoFactory.getMesaDAO().update(codigo, entity);
    }

    @Override
    public void configurarMesa(final UUID codigo, final MesaDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }
        if (UtilObjeto.esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo ni por defecto.");
        }
        MesaEntity entity = MesaEntityAssembler.getInstance().toEntity(domain);
        daoFactory.getMesaDAO().update(codigo, entity);
    }

    @Override
    public void registrarMesa(final MesaDomain domain) throws FondaControlException {
        if (UtilObjeto.esNulo(domain)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }

        String id = domain.getIdentificador();
        if (UtilTexto.esNula(id) || id.length() > 10) {
            throw BusinessLogicFondaControlException.reportar(
                    "El identificador de la mesa es obligatorio y no puede exceder 10 caracteres.");
        }

        EstadoMesaDomain estado = domain.getEstado();
        if (UtilObjeto.esNulo(estado)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El estado de la mesa es obligatorio.");
        }

        MesaEntity filtro = new MesaEntity();
        filtro.setNombre(id);
        List<MesaEntity> existentes = daoFactory.getMesaDAO().listByFilter(filtro);
        if (!existentes.isEmpty()) {
            throw BusinessLogicFondaControlException.reportar(
                    "Ya existe una mesa con el mismo identificador.");
        }

        UUID nuevoCodigo;
        do {
            nuevoCodigo = UtilUUID.generarNuevoUUID();
        } while (!daoFactory.getMesaDAO().listByCodigo(nuevoCodigo).isEmpty());

        MesaDomain nueva = MesaDomain.crear(nuevoCodigo, id, estado);
        MesaEntity entity = MesaEntityAssembler.getInstance().toEntity(nueva);
        daoFactory.getMesaDAO().create(entity);
    }

    @Override
    public void eliminarmesa(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código de la mesa no puede ser nulo ni por defecto.",
                    "Parámetro codigo inválido en eliminarmesa(...)"
            );
        }
        daoFactory.getMesaDAO().delete(codigo);
    }

    @Override
    public List<MesaDomain> consultarMesa(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            return MesaEntityAssembler.getInstance()
                    .toDomainList(daoFactory.getMesaDAO().listAll());
        }
        return MesaEntityAssembler.getInstance()
                .toDomainList(daoFactory.getMesaDAO().listByCodigo(codigo));
    }
}
