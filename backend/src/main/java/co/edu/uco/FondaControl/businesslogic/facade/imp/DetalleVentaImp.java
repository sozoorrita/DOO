package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.DetalleVentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.dto.DetalleVentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.businesslogic.facade.DetalleVentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;

public final class DetalleVentaImp implements DetalleVentaFacade {

    private final DAOFactory daoFactory;
    private final DetalleVentaBusinessLogic businessLogic;

    public DetalleVentaImp() {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new co.edu.uco.FondaControl.businesslogic.businesslogic.impl.DetalleVentaImpl(daoFactory);
    }

    @Override
    public void registrarDetalleVenta(final DetalleVentaDTO detalleVenta) throws FondaControlException {
        if (UtilObjeto.esNulo(detalleVenta)) {
            throw BusinessLogicFondaControlException.reportar("El detalle de venta no puede ser nulo.", "detalleVenta es nulo");
        }
        try {
            daoFactory.iniciarTransaccion();
            final DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(detalleVenta);
            businessLogic.registrarDetalleVenta(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error registrando detalle de venta.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarDetalleVenta(final DetalleVentaDTO detalleVenta) throws FondaControlException {
        if (UtilObjeto.esNulo(detalleVenta)) {
            throw BusinessLogicFondaControlException.reportar("El detalle de venta no puede ser nulo.", "detalleVenta es nulo");
        }
        final UUID codigo = detalleVenta.getCodigoDetalleVenta();
        try {
            daoFactory.iniciarTransaccion();
            final DetalleVentaDomain domain = DetalleVentaDTOAssembler.getInstancia().toDomain(detalleVenta);
            businessLogic.modificarDetalleVenta(codigo, domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error modificando detalle de venta.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarDetalleVenta(final DetalleVentaDTO detalleVenta) throws FondaControlException {
        if (UtilObjeto.esNulo(detalleVenta)) {
            throw BusinessLogicFondaControlException.reportar("El detalle de venta no puede ser nulo.", "detalleVenta es nulo");
        }
        final UUID codigo = detalleVenta.getCodigoDetalleVenta();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarDetalleVenta(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error eliminando detalle de venta.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<DetalleVentaDTO> consultarDetalleVenta(final DetalleVentaDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw BusinessLogicFondaControlException.reportar("El filtro no puede ser nulo.", "filtro es nulo");
        }
        try {
            final List<DetalleVentaDomain> listado = businessLogic.consultarDetalleVenta(
                    DetalleVentaDTOAssembler.getInstancia().toDomain(filtro));
            return DetalleVentaDTOAssembler.getInstancia().toDtoList(listado);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
