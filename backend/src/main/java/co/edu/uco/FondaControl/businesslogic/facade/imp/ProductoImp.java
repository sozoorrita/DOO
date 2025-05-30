package co.edu.uco.FondaControl.businesslogic.facade.imp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.ProductoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.ProductoImpl;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto.ProductoDTOAssembler;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.businesslogic.facade.ProductoFacade;
import co.edu.uco.FondaControl.dto.ProductoDTO;

@Service
public final class ProductoImp implements ProductoFacade {

    private final DAOFactory daoFactory;
    private final ProductoBusinessLogic businessLogic;

    public ProductoImp(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.businessLogic = new ProductoImpl(daoFactory);
    }

    @Override
    public void registrarProducto(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El producto a registrar no puede ser nulo y debe tener un nombre.",
                    "Producto inválido"
            );
        }
        try {
            daoFactory.iniciarTransaccion();
            ProductoDomain domain = ProductoDTOAssembler.getInstance().toDomain(producto);
            businessLogic.registrarProducto(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error registrando producto.",
                    e.getMessage(),
                    e
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarProducto(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El producto a modificar no puede ser nulo y debe tener un nombre.",
                    "Producto inválido"
            );
        }
        UUID codigo = producto.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            ProductoDomain domain = ProductoDTOAssembler.getInstance().toDomain(producto);
            businessLogic.modificarProducto(codigo, domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error modificando producto.",
                    e.getMessage(),
                    e
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarProducto(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilObjeto.esNulo(producto.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El producto a eliminar no puede ser nulo y debe contener un código.",
                    "Producto inválido"
            );
        }
        UUID codigo = producto.getCodigo();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarProducto(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar(
                    "Error eliminando producto.",
                    e.getMessage(),
                    e
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void consultarProductoPorCodigo(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilObjeto.esNulo(producto.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El producto a consultar no puede ser nulo y debe contener un código.",
                    "Producto inválido"
            );
        }
        try {
            ProductoDomain domain = businessLogic.consultarProductoPorCodigo(producto.getCodigo());
            ProductoDTOAssembler assembler = ProductoDTOAssembler.getInstance();
            ProductoDTO detalle = assembler.toDto(domain);
            producto.setNombre(detalle.getNombre());
            producto.setPrecioLugar(detalle.getPrecioLugar());
            producto.setPrecioLlevar(detalle.getPrecioLlevar());
            producto.getCodigo();
            producto.setLimiteCantidad(detalle.getLimiteCantidad());
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<ProductoDTO> consultarProducto(final ProductoDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El filtro para consultar productos no puede ser nulo.",
                    "Filtro inválido"
            );
        }
        try {
            ProductoDomain domainFilter = ProductoDTOAssembler.getInstance().toDomain(filtro);
            List<ProductoDomain> domains = businessLogic.consultarProducto(domainFilter);
            return ProductoDTOAssembler.getInstance().toDtoList(domains);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
