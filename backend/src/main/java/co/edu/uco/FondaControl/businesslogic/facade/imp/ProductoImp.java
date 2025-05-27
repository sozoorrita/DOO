package co.edu.uco.FondaControl.businesslogic.facade.imp;

import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.ProductoBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto.ProductoDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.businesslogic.facade.ProductoFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.ProductoDTO;

public final class ProductoImp implements ProductoFacade {

    private final DAOFactory daoFactory;
    private final ProductoBusinessLogic businessLogic;

    public ProductoImp() {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new co.edu.uco.FondaControl.businesslogic.businesslogic.impl.ProductoImpl(daoFactory);
    }

    @Override
    public void registrarProducto(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El producto a registrar no puede ser nulo y debe tener un nombre.", "producto inválido");
        }
        try {
            daoFactory.iniciarTransaccion();
            final ProductoDomain domain = ProductoDTOAssembler.getInstancia().toDomain(producto);
            businessLogic.registrarProducto(domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error registrando producto.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void modificarProducto(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El producto a modificar no puede ser nulo y debe tener un nombre.", "producto inválido");
        }
        final UUID codigo = producto.getCodigoProducto();
        try {
            daoFactory.iniciarTransaccion();
            final ProductoDomain domain = ProductoDTOAssembler.getInstancia().toDomain(producto);
            businessLogic.modificarProducto(codigo, domain);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error modificando producto.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public void eliminarProducto(final ProductoDTO producto) throws FondaControlException {
        if (UtilObjeto.esNulo(producto) || UtilTexto.getInstancia().esNula(producto.getNombre())) {
            throw BusinessLogicFondaControlException.reportar("El producto a eliminar no puede ser nulo y debe tener un nombre.", "producto inválido");
        }
        final UUID codigo = producto.getCodigoProducto();
        try {
            daoFactory.iniciarTransaccion();
            businessLogic.eliminarProducto(codigo);
            daoFactory.confirmarTransaccion();
        } catch (FondaControlException e) {
            daoFactory.cancelarTransaccion();
            throw e;
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw BusinessLogicFondaControlException.reportar("Error eliminando producto.", e.getMessage(), e);
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    @Override
    public List<ProductoDTO> consultarProducto(final ProductoDTO filtro) throws FondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw BusinessLogicFondaControlException.reportar("El filtro para consultar productos no puede ser nulo.", "filtro inválido");
        }
        try {
            final List<ProductoDomain> listado = businessLogic.consultarProducto(
                    ProductoDTOAssembler.getInstancia().toDomain(filtro));
            return ProductoDTOAssembler.getInstancia().toDtoList(listado);
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}