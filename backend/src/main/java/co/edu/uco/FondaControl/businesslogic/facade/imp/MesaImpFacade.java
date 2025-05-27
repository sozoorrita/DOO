package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.MesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.dto.MesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.MesaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.MesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.MesaDTO;

import java.util.List;
import java.util.UUID;

public final class MesaImpFacade implements MesaFacade {

    private final DAOFactory daoFactory;
    private final MesaBusinessLogic businessLogic;

    public MesaImpFacade() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new MesaImpl(daoFactory);
    }

    @Override
    public void evaluarMesa(final UUID codigo, final MesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        final var domain = MesaDTOAssembler.getInstancia().toDomain(dto);
        businessLogic.evaluarMesa(codigo, domain);
    }

    @Override
    public void configurarMesa(final UUID codigo, final MesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        final var domain = MesaDTOAssembler.getInstancia().toDomain(dto);
        businessLogic.configurarMesa(codigo, domain);
    }

    @Override
    public void registrarMesa(final MesaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("La mesa no puede ser nula.");
        }

        try {
            daoFactory.iniciarTransaccion();

            final var domain = MesaDTOAssembler.getInstancia().toDomain(dto);
            businessLogic.registrarMesa(domain);

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException exception) {
            daoFactory.cancelarTransaccion();
            throw exception;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();

            final var mensajeUsuario = "Se ha presentado un error al registrar la mesa.";
            final var mensajeTecnico = "Error técnico al registrar la mesa: " + exception.getMessage();

            throw BusinessLogicFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<MesaDTO> consultarMesa(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código de la mesa no puede ser nulo.");
        }

        final var resultado = businessLogic.consultarMesa(codigo);
        return MesaDTOAssembler.getInstancia().toDtoList(resultado);
    }

    private void validarEntrada(final UUID codigo, final MesaDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y la mesa no pueden ser nulos.");
        }
    }
}
