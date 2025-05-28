package co.edu.uco.FondaControl.data.dao.factory.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import co.edu.uco.FondaControl.data.dao.entity.categoria.impl.CategoriaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.detalleventa.impl.DetalleVentaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.impl.EstadoMesaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.formapago.impl.FormaPagoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql.IndicadorInventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql.InformeCajaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql.InventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.mesa.impl.postgresql.MesaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.producto.impl.ProductoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.rol.impl.RolPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql.SesionTrabajoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.subcategoria.impl.SubcategoriaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.tipoventa.impl.TipoVentaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql.UsuarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.venta.impl.VentaPostgreSQLDAO;

@Component
public class PostgreSQLDAOFactory extends DAOFactory {

    private final DataSource dataSource;
    private Connection connection;

    @Autowired
    public PostgreSQLDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected void abrirConexion() throws DataFondaControlException {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException ex) {
            throw DataFondaControlException.reportar(
                "No se pudo abrir conexión con la base de datos.",
                "Error SQL al obtener conexión desde DataSource", ex);
        }
    }

    @Override
    public void iniciarTransaccion() throws FondaControlException {
        try {
            abrirConexion();
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw DataFondaControlException.reportar(
                "No se pudo iniciar transacción.",
                "Error SQL durante setAutoCommit(false)", ex);
        }
    }

    @Override
    public void confirmarTransaccion() throws FondaControlException {
        try {
            connection.commit();
            cerrarConexion();
        } catch (SQLException ex) {
            throw DataFondaControlException.reportar(
                "No se pudo confirmar transacción.",
                "Error SQL durante commit()", ex);
        }
    }

    @Override
    public void cancelarTransaccion() throws FondaControlException {
        try {
            connection.rollback();
            cerrarConexion();
        } catch (SQLException ex) {
            throw DataFondaControlException.reportar(
                "No se pudo cancelar transacción.",
                "Error SQL durante rollback()", ex);
        }
    }

    @Override
    public void cerrarConexion() throws FondaControlException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            throw DataFondaControlException.reportar(
                "Error al cerrar conexión.",
                "Error SQL durante close()", ex);
        }
    }

    @Override
    public CategoriaPostgreSQLDAO getCategoriaDAO() {
        return new CategoriaPostgreSQLDAO(connection);
    }

    @Override
    public DetalleVentaPostgreSQLDAO getDetalleVentaDAO() {
        return new DetalleVentaPostgreSQLDAO(connection);
    }

    @Override
    public EstadoMesaPostgreSQLDAO getEstadoMesaDAO() {
        return new EstadoMesaPostgreSQLDAO(connection);
    }

    @Override
    public FormaPagoPostgreSQLDAO getFormaPagoDAO() {
        return new FormaPagoPostgreSQLDAO(connection);
    }

    @Override
    public IndicadorInventarioPostgreSQLDAO getIndicadorInventarioDAO() {
        return new IndicadorInventarioPostgreSQLDAO(connection);
    }

    @Override
    public InformeCajaPostgreSQLDAO getInformeCajaDAO() {
        return new InformeCajaPostgreSQLDAO(connection);
    }

    @Override
    public InventarioPostgreSQLDAO getInventarioDAO() {
        return new InventarioPostgreSQLDAO(connection);
    }

    @Override
    public MesaPostgreSQLDAO getMesaDAO() {
        return new MesaPostgreSQLDAO(connection);
    }

    @Override
    public ProductoPostgreSQLDAO getProductoDAO() {
        return new ProductoPostgreSQLDAO(connection);
    }

    @Override
    public RolPostgreSQLDAO getRolDAO() {
        return new RolPostgreSQLDAO(connection);
    }

    @Override
    public SesionTrabajoPostgreSQLDAO getSesionTrabajoDAO() {
        return new SesionTrabajoPostgreSQLDAO(connection);
    }

    @Override
    public SubcategoriaPostgreSQLDAO getSubcategoriaDAO() {
        return new SubcategoriaPostgreSQLDAO(connection);
    }

    @Override
    public TipoVentaPostgreSQLDAO getTipoVentaDAO() {
        return new TipoVentaPostgreSQLDAO(connection);
    }

    @Override
    public UsuarioPostgreSQLDAO getUsuarioDAO() {
        return new UsuarioPostgreSQLDAO(connection);
    }

    @Override
    public VentaPostgreSQLDAO getVentaDAO() {
        return new VentaPostgreSQLDAO(connection);
    }
}
