package co.edu.uco.FondaControl.data.dao.factory.postgresql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
        CategoriaPostgreSQLDAO daoReal = new CategoriaPostgreSQLDAO(connection);
        return crearProxy(CategoriaDAO.class, daoReal);
    }

    @Override
    public DetalleVentaDAO getDetalleVentaDAO() throws FondaControlException {
        abrirConexion();
        DetalleVentaPostgreSQLDAO daoReal = new DetalleVentaPostgreSQLDAO(connection);
        return crearProxy(DetalleVentaDAO.class, daoReal);
    }

    @Override
    public EstadoMesaDAO getEstadoMesaDAO() throws FondaControlException {
        abrirConexion();
        EstadoMesaPostgreSQLDAO daoReal = new EstadoMesaPostgreSQLDAO(connection);
        return crearProxy(EstadoMesaDAO.class, daoReal);
    }

    @Override
    public FormaPagoDAO getFormaPagoDAO() throws FondaControlException {
        abrirConexion();
        FormaPagoPostgreSQLDAO daoReal = new FormaPagoPostgreSQLDAO(connection);
        return crearProxy(FormaPagoDAO.class, daoReal);
    }

    @Override
    public IndicadorInventarioDAO getIndicadorInventarioDAO() throws FondaControlException {
        abrirConexion();
        IndicadorInventarioPostgreSQLDAO daoReal = new IndicadorInventarioPostgreSQLDAO(connection);
        return crearProxy(IndicadorInventarioDAO.class, daoReal);
    }

    @Override
    public InformeCajaDAO getInformeCajaDAO() throws FondaControlException {
        abrirConexion();
        InformeCajaPostgreSQLDAO daoReal = new InformeCajaPostgreSQLDAO(connection);
        return crearProxy(InformeCajaDAO.class, daoReal);
    }

    @Override
    public InventarioDAO getInventarioDAO() throws FondaControlException {
        abrirConexion();
        InventarioPostgreSQLDAO daoReal = new InventarioPostgreSQLDAO(connection);
        return crearProxy(InventarioDAO.class, daoReal);
    }

    @Override
    public MesaDAO getMesaDAO() throws FondaControlException {
        abrirConexion();
        MesaPostgreSQLDAO daoReal = new MesaPostgreSQLDAO(connection);
        return crearProxy(MesaDAO.class, daoReal);
    }

    @Override
    public ProductoDAO getProductoDAO() throws FondaControlException {
        abrirConexion();
        ProductoPostgreSQLDAO daoReal = new ProductoPostgreSQLDAO(connection);
        return crearProxy(ProductoDAO.class, daoReal);
    }

    @Override
    public RolDAO getRolDAO() throws FondaControlException {
        abrirConexion();
        RolPostgreSQLDAO daoReal = new RolPostgreSQLDAO(connection);
        return crearProxy(RolDAO.class, daoReal);
    }

    @Override
    public SesionTrabajoDAO getSesionTrabajoDAO() throws FondaControlException {
        abrirConexion();
        SesionTrabajoPostgreSQLDAO daoReal = new SesionTrabajoPostgreSQLDAO(connection);
        return crearProxy(SesionTrabajoDAO.class, daoReal);
    }

    @Override
    public SubcategoriaDAO getSubcategoriaDAO() throws FondaControlException {
        abrirConexion();
        SubcategoriaPostgreSQLDAO daoReal = new SubcategoriaPostgreSQLDAO(connection);
        return crearProxy(SubcategoriaDAO.class, daoReal);
    }

    @Override
    public TipoVentaDAO getTipoVentaDAO() throws FondaControlException {
        abrirConexion();
        TipoVentaPostgreSQLDAO daoReal = new TipoVentaPostgreSQLDAO(connection);
        return crearProxy(TipoVentaDAO.class, daoReal);
    }

    @Override
    public UsuarioDAO getUsuarioDAO() throws FondaControlException {
        abrirConexion();
        UsuarioPostgreSQLDAO daoReal = new UsuarioPostgreSQLDAO(connection);
        return crearProxy(UsuarioDAO.class, daoReal);
    }

    @Override
    public VentaDAO getVentaDAO() throws FondaControlException {
        abrirConexion();
        VentaPostgreSQLDAO daoReal = new VentaPostgreSQLDAO(connection);
        return crearProxy(VentaDAO.class, daoReal);
    }

    @SuppressWarnings("unchecked")
    private <T> T crearProxy(Class<T> daoInterface, Object daoReal) {
        InvocationHandler handler = (proxy, method, args) -> {
            try {
                return method.invoke(daoReal, args);
            } finally {
                // Solo cierra conexión si está en modo auto-commit (no hay transacción activa)
                try {
                    if (connection != null && connection.getAutoCommit()) {
                        cerrarConexion();
                    }
                } catch (SQLException e) {
                    // Si falla al verificar o cerrar, lanzamos excepción de DAO
                    throw DataFondaControlException.reportar(
                            "Error al cerrar conexión tras invocar método DAO",
                            "PostgreSQLDAOFactory.crearProxy: " + e.getMessage(),
                            e
                    );
                }
            }
        };

        return (T) Proxy.newProxyInstance(
                daoInterface.getClassLoader(),
                new Class<?>[]{daoInterface},
                handler
        );
    }
}
