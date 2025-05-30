package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InventarioBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InventarioImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.dto.InventarioDTOAssembler;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.InventarioFacade;
import co.edu.uco.FondaControl.dto.InventarioDTO;

@Service
public final class InventarioImp implements InventarioFacade {

    private final DAOFactory daoFactory;
    private final InventarioBusinessLogic businessLogic;

    public InventarioImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new InventarioImpl(daoFactory);
    }

    @Override
    public void actualizarCantidadEnInventario(UUID codigo, InventarioDTO inventario) throws FondaControlException {
        validarEntrada(codigo, inventario);
        InventarioDomain domain = InventarioDTOAssembler.getInstancia().toDomain(inventario);
        businessLogic.actualizarCantidadEnInventario(codigo, domain);
    }

    @Override
    public void consultarCantidadInventario(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo.");
        }
        businessLogic.consultarCantidadInventario(codigo);
    }

    @Override
    public void gestionarInventarioManualmente(InventarioDTO inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        try {
            daoFactory.iniciarTransaccion();
            InventarioDomain domain = InventarioDTOAssembler.getInstancia().toDomain(inventario);
            businessLogic.gestionarInventarioManualmente(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha producido un error al gestionar el inventario.",
                    "Error técnico al ejecutar la lógica de negocio para gestionar el inventario: " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void registrarInventario(InventarioDTO inventario) throws FondaControlException {
        if (UtilObjeto.esNulo(inventario)) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        try {
            daoFactory.iniciarTransaccion();
            InventarioDomain domain = InventarioDTOAssembler.getInstancia().toDomain(inventario);
            businessLogic.registrarInventario(domain);
            // Propagar código generado al DTO
            inventario.setCodigo(domain.getCodigo());
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha producido un error al registrar el inventario.",
                    "Error técnico al ejecutar la lógica de negocio para registrar el inventario: " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void consultarInventario(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo.");
        }
        businessLogic.consultarInventario(codigo);
    }

    @Override
    public void eliminarInventario(UUID codigo) throws FondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código del inventario no puede ser nulo.");
        }
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarInventario(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Se ha producido un error al eliminar el inventario.",
                    "Error técnico al ejecutar la lógica de negocio para eliminar el inventario: " + ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    private void validarEntrada(UUID codigo, InventarioDTO dto) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(dto)) {
            throw new IllegalArgumentException("El código y el inventario no pueden ser nulos.");
        }
    }
}
