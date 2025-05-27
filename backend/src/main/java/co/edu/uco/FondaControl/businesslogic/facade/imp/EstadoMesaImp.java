package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.EstadoMesaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.dto.EstadoMesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.EstadoMesaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.EstadoMesaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;

import java.util.List;
import java.util.UUID;


public final class EstadoMesaImp implements EstadoMesaFacade {

    private final DAOFactory daoFactory;
    private final EstadoMesaBusinessLogic businessLogic;

    public EstadoMesaImp() throws DataFondaControlException {
        daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        businessLogic = new EstadoMesaImpl(daoFactory);
    }

    @Override
    public void evaluarEstadoMesa(final UUID codigo, final EstadoMesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        final var domain = EstadoMesaDTOAssembler.getInstancia().toDomain(dto);
        businessLogic.evaluarEstadoMesa(codigo, domain);
    }

    @Override
    public void configurarEstadoMesa(final UUID codigo, final EstadoMesaDTO dto) throws FondaControlException {
        validarEntrada(codigo, dto);
        final var domain = EstadoMesaDTOAssembler.getInstancia().toDomain(dto);
        businessLogic.configurarEstadoMesa(codigo, domain);
    }

    @Override
    public void registrarEstadoMesa(final EstadoMesaDTO dto) throws FondaControlException {
        if (UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El estado de mesa no puede ser nulo.");
        }

        try {
            daoFactory.iniciarTransaccion();

            final var domain = EstadoMesaDTOAssembler.getInstancia().toDomain(dto);
            businessLogic.registrarEstadoMesa(domain);

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException exception) {
            daoFactory.cancelarTransaccion();
            throw exception;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();

            final var mensajeUsuario = "Se ha producido un error al registrar el estado de mesa.";
            final var mensajeTecnico = "Error técnico al intentar registrar el estado de mesa: " + exception.getMessage();

            throw BusinessLogicFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<EstadoMesaDTO> consultarEstadoMesa(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código de estado de mesa no puede ser nulo.");
        }

        final var resultado = businessLogic.consultarEstadoMesa(codigo);
        return EstadoMesaDTOAssembler.getInstancia().toDtoList(resultado);
    }

    private void validarEntrada(final UUID codigo, final EstadoMesaDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y el estado de mesa no pueden ser nulos.");
        }
    }
}
