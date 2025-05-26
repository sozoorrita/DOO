package co.edu.uco.FondaControl.data.dao.factory;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
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
import co.edu.uco.FondaControl.data.dao.entity.venta.VentaDAO;
import co.edu.uco.FondaControl.data.dao.factory.postgresql.PostgreSQLDAOFactory;

public abstract class DAOFactory {
    public static DAOFactory getDAOFactory(Factory factory) throws DataFondaControlException {
        switch (factory) {
            case POSTGRESQL:
                return new PostgreSQLDAOFactory();
            default:
                var mensajeUsuario = "Se ha presentado un problema tratando de obtener la información de la fuente de datos contra la cual se llevarán a cabo las operaciones.";
                var mensajeTecnico = "Se solicitó la factory " + factory + " pero esta no está implementada.";
                throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico);
        }
    }

    protected abstract void abrirConexion() throws DataFondaControlException;

    public abstract void iniciarTransaccion() throws DataFondaControlException;

    public abstract void confirmarTransaccion() throws DataFondaControlException;

    public abstract void cancelarTransaccion() throws DataFondaControlException;

    public abstract void cerrarConexion() throws DataFondaControlException;

    public abstract IndicadorInventarioDAO getIndicadorInventarioDAO() throws DataFondaControlException;

    public abstract InformeCajaDAO getInformeCajaDAO() throws DataFondaControlException;

    public abstract InventarioDAO getInventarioDAO() throws DataFondaControlException;

    public abstract UsuarioDAO getUsuarioDAO() throws DataFondaControlException;

    public abstract SesionTrabajoDAO getSesionTrabajoDAO() throws DataFondaControlException;

    public abstract MesaDAO getMesaDAO() throws DataFondaControlException;

    public abstract EstadoMesaDAO getEstadoMesaDAO() throws DataFondaControlException;

    public abstract CategoriaDAO getCategoriaDAO() throws DataFondaControlException;

    public abstract SubcategoriaDAO getSubcategoriaDAO() throws DataFondaControlException;

    public abstract TipoVentaDAO getTipoVentaDAO() throws DataFondaControlException;

    public abstract FormaPagoDAO getFormaPagoDAO() throws DataFondaControlException;

    public abstract RolDAO getRolDAO() throws DataFondaControlException;

    public abstract ProductoDAO getProductoDAO() throws DataFondaControlException;

    public abstract DetalleVentaDAO getDetalleVentaDAO() throws DataFondaControlException;

    public abstract VentaDAO getVentaDAO() throws DataFondaControlException;
}
