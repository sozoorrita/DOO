package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.DetalleVentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.entity.DetalleVentaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import java.util.List;
import java.util.UUID;

public final class DetalleVentaImpl implements DetalleVentaBusinessLogic {

    private final DAOFactory daoFactory;

    public DetalleVentaImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarDetalleVenta(final DetalleVentaDomain detalleVenta) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(detalleVenta) 
                || UtilTexto.getInstancia().esNula(detalleVenta.getNombreProducto())) {
            throw new IllegalArgumentException("El detalle de venta a registrar no puede ser nulo y debe contener un nombre de producto.");
        }
        if (detalleVenta.getPrecioAplicado() < 0) {
            throw new IllegalArgumentException("El precio aplicado no puede ser negativo.");
        }
        if (detalleVenta.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        // Generar un código único para el detalle de venta
        final var codigo = UtilUUID.generarNuevoUUID();
        detalleVenta.setCodigoDetalleVenta(codigo);

        var entity = DetalleVentaEntityAssembler.getInstance().toEntity(detalleVenta);
        daoFactory.getDetalleVentaDAO().create(entity);
    }

    @Override
    public void modificarDetalleVenta(final UUID codigo, final DetalleVentaDomain detalleVenta) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del detalle de venta a modificar no puede ser nulo.");
        }
        if (UtilObjeto.getInstancia().esNulo(detalleVenta)) {
            throw new IllegalArgumentException("El detalle de venta a modificar no puede ser nulo.");
        }
        var entity = DetalleVentaEntityAssembler.getInstance().toEntity(detalleVenta);
        daoFactory.getDetalleVentaDAO().update(codigo, entity);
    }

    @Override
    public void eliminarDetalleVenta(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del detalle de venta a eliminar no puede ser nulo.");
        }
        daoFactory.getDetalleVentaDAO().delete(codigo);
    }

    @Override
    public List<DetalleVentaDomain> consultarDetalleVenta(final DetalleVentaDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro para consultar detalles de venta no puede ser nulo.");
        }
        var entityFilter = DetalleVentaEntityAssembler.getInstance().toEntity(filtro);
        var entities = daoFactory.getDetalleVentaDAO().listByFilter(entityFilter);
        return DetalleVentaEntityAssembler.getInstance().toDomainList(entities);
    }
}