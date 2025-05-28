package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.VentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.entity.VentaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import java.util.List;
import java.util.UUID;

public final class VentaImpl implements VentaBusinessLogic {

    private final DAOFactory daoFactory;

    public VentaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarVenta(final VentaDomain venta) throws FondaControlException {
        validarVenta(venta);

        var entity = VentaEntityAssembler.getInstance().toEntity(venta);
        daoFactory.getVentaDAO().create(entity);
    }

    @Override
    public void modificarVenta(final UUID codigo, final VentaDomain venta) throws FondaControlException {
        validarCodigoVenta(codigo);
        validarVenta(venta);

        venta.setCodigoVenta(codigo);
        var entity = VentaEntityAssembler.getInstance().toEntity(venta);
        daoFactory.getVentaDAO().update(codigo, entity);
    }

    @Override
    public void eliminarVenta(final UUID codigo) throws FondaControlException {
        validarCodigoVenta(codigo);

        daoFactory.getVentaDAO().delete(codigo);
    }

    @Override
    public List<VentaDomain> consultarVenta(final VentaDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro para consultar ventas no puede ser nulo.");
        }

        var entityFiltro = VentaEntityAssembler.getInstance().toEntity(filtro);
        var resultados = daoFactory.getVentaDAO().listByFilter(entityFiltro);

        return VentaEntityAssembler.getInstance().toDomainList(resultados);
    }

    private void validarVenta(final VentaDomain venta) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(venta)) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }

        if (venta.getTotalVenta() < 0) {
            throw new IllegalArgumentException("El total de la venta no puede ser negativo.");
        }

        if (UtilUUID.esValorDefecto(venta.getCodigoFormaPago()) ||
                UtilUUID.esValorDefecto(venta.getCodigoTipoVenta()) ||
                UtilUUID.esValorDefecto(venta.getCodigoSesionTrabajo()) ||
                UtilUUID.esValorDefecto(venta.getCodigoMesa())) {
            throw new IllegalArgumentException("La venta debe tener datos válidos en forma de pago, tipo de venta, sesión de trabajo y mesa.");
        }
    }

    private void validarCodigoVenta(final UUID codigo) {
        if (UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código de la venta no puede ser nulo ni el valor por defecto.");
        }
    }
}