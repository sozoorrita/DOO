package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.VentaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.entity.VentaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
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
        if (UtilObjeto.getInstancia().esNulo(venta)) {
            throw new IllegalArgumentException("La venta a registrar no puede ser nula.");
        }
        var entity = VentaEntityAssembler.getInstancia().toEntity(venta);
        daoFactory.getVentaDAO().create(entity);
    }

    @Override
    public void modificarVenta(final UUID codigo, final VentaDomain venta) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código de la venta a modificar no puede ser nulo.");
        }
        if (UtilObjeto.getInstancia().esNulo(venta)) {
            throw new IllegalArgumentException("La venta a modificar no puede ser nula.");
        }
        var entity = VentaEntityAssembler.getInstancia().toEntity(venta);
        daoFactory.getVentaDAO().update(codigo, entity);
    }

    @Override
    public void eliminarVenta(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código de la venta a eliminar no puede ser nulo.");
        }
        daoFactory.getVentaDAO().delete(codigo);
    }

    @Override
    public List<VentaDomain> consultarVenta(final VentaDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro para consultar ventas no puede ser nulo.");
        }
        var entityFilter = VentaEntityAssembler.getInstancia().toEntity(filtro);
        var entities = daoFactory.getVentaDAO().listByFilter(entityFilter);
        return VentaEntityAssembler.getInstancia().toDomainList(entities);
    }
}