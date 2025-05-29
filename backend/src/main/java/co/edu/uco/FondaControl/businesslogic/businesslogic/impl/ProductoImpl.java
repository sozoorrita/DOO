package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.ProductoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.entity.ProductoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.ProductoEntity;

import java.util.List;
import java.util.UUID;

public final class ProductoImpl implements ProductoBusinessLogic {

    private final DAOFactory daoFactory;

    public ProductoImpl(final DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void registrarProducto(final ProductoDomain producto) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(producto)) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        String nombre = producto.getNombre();
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del producto es obligatorio.");
        }
        
        if (producto.getPrecioLugar() < 0 || producto.getPrecioLlevar() < 0) {
            throw BusinessLogicFondaControlException.reportar("Los precios del producto no pueden ser negativos.");
        }
        if (UtilObjeto.getInstancia().esNulo(producto.getSubcategoria())) {
            throw BusinessLogicFondaControlException.reportar("La subcategoría del producto es obligatoria.");
        }
        
        UUID codigo;
        do {
            codigo = UtilUUID.generarNuevoUUID();
        } while (!daoFactory.getProductoDAO().listByCodigo(codigo).isEmpty());
        
        ProductoDomain aCrear = new ProductoDomain(
            codigo,
            nombre,
            producto.getPrecioLugar(),
            producto.getPrecioLlevar(),
            producto.getSubcategoria(),
            producto.getLimiteCantidad()
        );
        ProductoEntity entity = ProductoEntityAssembler.getInstance().toEntity(aCrear);
        daoFactory.getProductoDAO().create(entity);
    }

    @Override
    public void modificarProducto(final UUID codigo, final ProductoDomain producto) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código del producto no puede ser nulo ni por defecto.");
        }
        if (UtilObjeto.getInstancia().esNulo(producto)) {
            throw new IllegalArgumentException("El producto a modificar no puede ser nulo.");
        }
        String nombre = producto.getNombre();
        if (UtilTexto.getInstancia().esNula(nombre)) {
            throw BusinessLogicFondaControlException.reportar("El nombre del producto es obligatorio.");
        }
        if (producto.getPrecioLugar() < 0 || producto.getPrecioLlevar() < 0) {
            throw BusinessLogicFondaControlException.reportar("Los precios del producto no pueden ser negativos.");
        }
        if (UtilObjeto.getInstancia().esNulo(producto.getSubcategoria())) {
            throw BusinessLogicFondaControlException.reportar("La subcategoría del producto es obligatoria.");
        }
        ProductoEntity entity = ProductoEntityAssembler.getInstance().toEntity(producto);
        daoFactory.getProductoDAO().update(codigo, entity);
    }

    @Override
    public void eliminarProducto(final UUID codigo) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(codigo) || UtilUUID.esValorDefecto(codigo)) {
            throw new IllegalArgumentException("El código del producto no puede ser nulo ni por defecto.");
        }
        daoFactory.getProductoDAO().delete(codigo);
    }

    @Override
    public List<ProductoDomain> consultarProducto(final ProductoDomain filtro) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro de producto no puede ser nulo.");
        }
        ProductoEntity entidadFiltro = ProductoEntityAssembler.getInstance().toEntity(filtro);
        List<ProductoEntity> entidades = daoFactory.getProductoDAO().listByFilter(entidadFiltro);
        return ProductoEntityAssembler.getInstance().toDomainList(entidades);
    }
}
