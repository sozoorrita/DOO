package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.VentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.dto.VentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.businesslogic.facade.VentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.VentaDTO;

public final class VentaImp implements VentaFacade {

    private final DAOFactory daoFactory;
    private final VentaBusinessLogic businessLogic;

    public VentaImp() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new co.edu.uco.FondaControl.businesslogic.businesslogic.impl.VentaImpl(daoFactory);
    }

    @Override
    public void registrarVenta(final VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta)) {
            throw BusinessLogicFondaControlException.reportar("La venta a registrar no puede ser nula.", "venta es nula");
        }
        try {
            daoFactory.iniciarTransaccion();
            final VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(venta);
            businessLogic.registrarVenta(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error registrando venta.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarVenta(final VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta)) {
            throw BusinessLogicFondaControlException.reportar("La venta a modificar no puede ser nula.", "venta es nula");
        }
        final UUID codigo = venta.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            final VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(venta);
            businessLogic.modificarVenta(codigo, domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error modificando venta.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarVenta(final VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta)) {
            throw BusinessLogicFondaControlException.reportar("La venta a eliminar no puede ser nula.", "venta es nula");
        }
        final UUID codigo = venta.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarVenta(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error eliminando venta.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<VentaDTO> consultarVenta(final VentaDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw BusinessLogicFondaControlException.reportar("El filtro de venta no puede ser nulo.", "filtro es nulo");
        }
        try {
            daoFactory.iniciarTransaccion();
            final VentaDomain domainFiltro = VentaDTOAssembler.getInstancia().toDomain(filtro);
            final List<VentaDomain> dominios = businessLogic.consultarVenta(domainFiltro);
            daoFactory.confirmarTransaccion();
            return VentaDTOAssembler.getInstancia().toDtoList(dominios);
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error consultando ventas.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
