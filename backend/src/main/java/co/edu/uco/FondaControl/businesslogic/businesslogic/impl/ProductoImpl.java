package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.ProductoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.entity.ProductoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import java.util.List;
import java.util.UUID;

public final class ProductoImpl implements ProductoBusinessLogic {

    private final DAOFactory daoFactory;

    public ProductoImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarProducto(final ProductoDomain producto) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(producto)
                || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw new IllegalArgumentException("El producto a registrar no puede ser nulo y debe tener un nombre.");
        }
        var entity = ProductoEntityAssembler.getInstancia().toEntity(producto);
        daoFactory.getProductoDAO().create(entity);
    }

    @Override
    public void modificarProducto(final UUID codigo, final ProductoDomain producto) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del producto a modificar no puede ser nulo.");
        }
        if (UtilObjeto.getInstancia().esNulo(producto)
                || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw new IllegalArgumentException("El producto a modificar debe tener un nombre válido.");
        }
        var entity = ProductoEntityAssembler.getInstancia().toEntity(producto);
        daoFactory.getProductoDAO().update(codigo, entity);
    }

    @Override
    public void eliminarProducto(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo)) {
            throw new IllegalArgumentException("El código del producto a eliminar no puede ser nulo.");
        }
        daoFactory.getProductoDAO().delete(codigo);
    }

    @Override
    public List<ProductoDomain> consultarProducto(final ProductoDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro para consultar productos no puede ser nulo.");
        }
        var entityFilter = ProductoEntityAssembler.getInstancia().toEntity(filtro);
        var entities = daoFactory.getProductoDAO().listByFilter(entityFilter);
        return ProductoEntityAssembler.getInstancia().toDomainList(entities);
    }
}