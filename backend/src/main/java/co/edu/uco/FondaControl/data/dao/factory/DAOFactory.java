package co.edu.uco.FondaControl.data.dao.factory;

import javax.sql.DataSource;

import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.postgresql.PostgreSQLDAOFactory;
import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.data.dao.entity.detalleventa.DetalleVentaDAO;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.EstadoMesaDAO;
import co.edu.uco.FondaControl.data.dao.entity.formapago.FormaPagoDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.mesa.MesaDAO;
import co.edu.uco.FondaControl.data.dao.entity.producto.ProductoDAO;
import co.edu.uco.FondaControl.data.dao.entity.rol.RolDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.data.dao.entity.subcategoria.SubcategoriaDAO;
import co.edu.uco.FondaControl.data.dao.entity.tipoventa.TipoVentaDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.venta.VentaDAO;

public abstract class DAOFactory {

    public static DAOFactory getFactory(Factory factory, DataSource dataSource) throws FondaControlException {
        switch (factory) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory(dataSource);
            default:
                throw new IllegalArgumentException("No se ha implementado la fabrica para " + factory);
        }
    }

    protected abstract void abrirConexion() throws FondaControlException;
    public abstract void iniciarTransaccion() throws FondaControlException;
    public abstract void confirmarTransaccion() throws FondaControlException;
    public abstract void cancelarTransaccion() throws FondaControlException;
    public abstract void cerrarConexion() throws FondaControlException;

    public abstract CategoriaDAO getCategoriaDAO() throws FondaControlException;
    public abstract DetalleVentaDAO getDetalleVentaDAO() throws FondaControlException;
    public abstract EstadoMesaDAO getEstadoMesaDAO() throws FondaControlException;
    public abstract FormaPagoDAO getFormaPagoDAO() throws FondaControlException;
    public abstract IndicadorInventarioDAO getIndicadorInventarioDAO() throws FondaControlException;
    public abstract InformeCajaDAO getInformeCajaDAO() throws FondaControlException;
    public abstract InventarioDAO getInventarioDAO() throws FondaControlException;
    public abstract MesaDAO getMesaDAO() throws FondaControlException;
    public abstract ProductoDAO getProductoDAO() throws FondaControlException;
    public abstract RolDAO getRolDAO() throws FondaControlException;
    public abstract SesionTrabajoDAO getSesionTrabajoDAO() throws FondaControlException;
    public abstract SubcategoriaDAO getSubcategoriaDAO() throws FondaControlException;
    public abstract TipoVentaDAO getTipoVentaDAO() throws FondaControlException;
    public abstract UsuarioDAO getUsuarioDAO() throws FondaControlException;
    public abstract VentaDAO getVentaDAO() throws FondaControlException;
}
