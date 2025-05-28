// src/main/java/co/edu/uco/FondaControl/data/dao/factory/postgresql/PostgreSQLDAOFactory.java
package co.edu.uco.FondaControl.data.dao.factory.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.data.dao.entity.categoria.impl.CategoriaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.detalleventa.DetalleVentaDAO;
import co.edu.uco.FondaControl.data.dao.entity.detalleventa.impl.DetalleVentaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.EstadoMesaDAO;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.impl.EstadoMesaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.formapago.FormaPagoDAO;
import co.edu.uco.FondaControl.data.dao.entity.formapago.impl.FormaPagoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql.IndicadorInventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql.InformeCajaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql.InventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.mesa.MesaDAO;
import co.edu.uco.FondaControl.data.dao.entity.mesa.impl.postgresql.MesaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.producto.ProductoDAO;
import co.edu.uco.FondaControl.data.dao.entity.producto.impl.ProductoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.rol.RolDAO;
import co.edu.uco.FondaControl.data.dao.entity.rol.impl.RolPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql.SesionTrabajoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.subcategoria.SubcategoriaDAO;
import co.edu.uco.FondaControl.data.dao.entity.subcategoria.impl.SubcategoriaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.tipoventa.TipoVentaDAO;
import co.edu.uco.FondaControl.data.dao.entity.tipoventa.impl.TipoVentaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql.UsuarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.venta.VentaDAO;
import co.edu.uco.FondaControl.data.dao.entity.venta.impl.VentaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class PostgreSQLDAOFactory extends DAOFactory {
    private final DataSource dataSource;
    private Connection connection;

    public PostgreSQLDAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
	public void abrirConexion() throws FondaControlException {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "No fue posible abrir la conexión",
                "PostgreSQLDAOFactory.abrirConexion: " + e.getMessage(),
                e
            );
        }
    }

    @Override
    public void cerrarConexion() throws FondaControlException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "No fue posible cerrar la conexión",
                "PostgreSQLDAOFactory.cerrarConexion: " + e.getMessage(),
                e
            );
        }
    }

    @Override
    public void iniciarTransaccion() throws FondaControlException {
        abrirConexion();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al iniciar transacción",
                "PostgreSQLDAOFactory.iniciarTransaccion: " + e.getMessage(),
                e
            );
        }
    }

    @Override
    public void confirmarTransaccion() throws FondaControlException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al confirmar transacción",
                "PostgreSQLDAOFactory.confirmarTransaccion: " + e.getMessage(),
                e
            );
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public void cancelarTransaccion() throws FondaControlException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al cancelar transacción",
                "PostgreSQLDAOFactory.cancelarTransaccion: " + e.getMessage(),
                e
            );
        } finally {
            cerrarConexion();
        }
    }

    @Override
    public CategoriaDAO getCategoriaDAO() throws FondaControlException {
        abrirConexion();
        return new CategoriaPostgreSQLDAO(connection);
    }

    @Override
    public DetalleVentaDAO getDetalleVentaDAO() throws FondaControlException {
        abrirConexion();
        return new DetalleVentaPostgreSQLDAO(connection);
    }

    @Override
    public EstadoMesaDAO getEstadoMesaDAO() throws FondaControlException {
        abrirConexion();
        return new EstadoMesaPostgreSQLDAO(connection);
    }

    @Override
    public FormaPagoDAO getFormaPagoDAO() throws FondaControlException {
        abrirConexion();
        return new FormaPagoPostgreSQLDAO(connection);
    }

    @Override
    public IndicadorInventarioDAO getIndicadorInventarioDAO() throws FondaControlException {
        abrirConexion();
        return new IndicadorInventarioPostgreSQLDAO(connection);
    }

    @Override
    public InformeCajaDAO getInformeCajaDAO() throws FondaControlException {
        abrirConexion();
        return new InformeCajaPostgreSQLDAO(connection);
    }

    @Override
    public InventarioDAO getInventarioDAO() throws FondaControlException {
        abrirConexion();
        return new InventarioPostgreSQLDAO(connection);
    }

    @Override
    public MesaDAO getMesaDAO() throws FondaControlException {
        abrirConexion();
        return new MesaPostgreSQLDAO(connection);
    }

    @Override
    public ProductoDAO getProductoDAO() throws FondaControlException {
        abrirConexion();
        return new ProductoPostgreSQLDAO(connection);
    }

    @Override
    public RolDAO getRolDAO() throws FondaControlException {
        abrirConexion();
        return new RolPostgreSQLDAO(connection);
    }

    @Override
    public SesionTrabajoDAO getSesionTrabajoDAO() throws FondaControlException {
        abrirConexion();
        return new SesionTrabajoPostgreSQLDAO(connection);
    }

    @Override
    public SubcategoriaDAO getSubcategoriaDAO() throws FondaControlException {
        abrirConexion();
        return new SubcategoriaPostgreSQLDAO(connection);
    }

    @Override
    public TipoVentaDAO getTipoVentaDAO() throws FondaControlException {
        abrirConexion();
        return new TipoVentaPostgreSQLDAO(connection);
    }

    @Override
    public UsuarioDAO getUsuarioDAO() throws FondaControlException {
        abrirConexion();
        return new UsuarioPostgreSQLDAO(connection);
    }

    @Override
    public VentaDAO getVentaDAO() throws FondaControlException {
        abrirConexion();
        return new VentaPostgreSQLDAO(connection);
    }
}
