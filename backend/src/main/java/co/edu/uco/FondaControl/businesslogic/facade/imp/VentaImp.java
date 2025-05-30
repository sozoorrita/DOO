package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.VentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.VentaImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.dto.VentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.facade.VentaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.dto.VentaDTO;

@Service
public final class VentaImp implements VentaFacade {

    private final DAOFactory daoFactory;
    private final VentaBusinessLogic businessLogic;

    public VentaImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new VentaImpl(daoFactory);
    }

    @Override
    public void registrarVenta(VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta)) {
            throw BusinessLogicFondaControlException.reportar(
                    "La venta a registrar no puede ser nula.",
                    "DTO de venta es nulo"
            );
        }
        try {
            daoFactory.iniciarTransaccion();
            VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(venta);
            businessLogic.registrarVenta(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error registrando venta.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarVenta(VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta) || UtilObjeto.esNulo(venta.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La venta a modificar no puede ser nula y debe contener un código.",
                    "DTO de venta inválido"
            );
        }
        UUID codigo = venta.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            VentaDomain domain = VentaDTOAssembler.getInstancia().toDomain(venta);
            businessLogic.modificarVenta(codigo, domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error modificando venta.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarVenta(VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta) || UtilObjeto.esNulo(venta.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La venta a eliminar no puede ser nula y debe contener un código.",
                    "DTO de venta inválido"
            );
        }
        UUID codigo = venta.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarVenta(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error eliminando venta.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void consultarVentaPorCodigo(VentaDTO venta) throws FondaControlException {
        if (UtilObjeto.esNulo(venta) || UtilObjeto.esNulo(venta.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La venta a consultar no puede ser nula y debe contener un código.",
                    "DTO de venta inválido"
            );
        }
        UUID codigo = venta.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.consultarVentaPorCodigo(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException ex) {
            daoFactory.cancelarTransaccion();
            throw ex;
        } catch (Exception ex) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error consultando venta.",
                    ex.getMessage(),
                    ex
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<VentaDTO> consultarVenta(VentaDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El filtro de venta no puede ser nulo.",
                    "DTO de filtro inválido"
            );
        }
        try {
            VentaDomain domainFilter = VentaDTOAssembler.getInstancia().toDomain(filtro);
            List<VentaDomain> domains = businessLogic.consultarVenta(domainFilter);
            return VentaDTOAssembler.getInstancia().toDtoList(domains);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
