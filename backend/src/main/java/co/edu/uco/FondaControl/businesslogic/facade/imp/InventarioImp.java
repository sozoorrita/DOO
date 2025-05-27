package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.dto.InventarioDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InventarioImpl;
import co.edu.uco.FondaControl.businesslogic.facade.InventarioFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.InventarioDTO;

import java.util.UUID;

public final class InventarioImp implements InventarioFacade {

    private final DAOFactory daoFactory;
    private final InventarioBusinessLogic businessLogic;

    public InventarioImp() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new InventarioImpl(daoFactory);
    }

    @Override
    public void actualizarCantidadEnInventario(final UUID codigo, final InventarioDTO inventario) throws FondaControlException {
        validarEntrada(codigo, inventario);
        final var domain = InventarioDTOAssembler.getInstancia().toDomain(inventario);
        businessLogic.actualizarCantidadEnInventario(codigo, domain);
    }

    @Override
    public void consultarCantidadInventario(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo.");
        }
        businessLogic.consultarCantidadInventario(codigo);
    }

    @Override
    public void gestionarInventarioManualmente(final InventarioDTO inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }

        try {
            daoFactory.iniciarTransaccion();

            final var domain = InventarioDTOAssembler.getInstancia().toDomain(inventario);
            businessLogic.gestionarInventarioManualmente(domain);

            daoFactory.confirmarTransaccion();
        } catch (FondaControlException exception) {
            daoFactory.cancelarTransaccion();
            throw exception;
        } catch (Exception exception) {
            daoFactory.cancelarTransaccion();

            final var mensajeUsuario = "Se ha producido un error al gestionar el inventario.";
            final var mensajeTecnico = "Error técnico al ejecutar la lógica de negocio para gestionar el inventario: " + exception.getMessage();

            throw BusinessLogicFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    private void validarEntrada(final UUID codigo, final InventarioDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y el inventario no pueden ser nulos.");
        }
    }
}
