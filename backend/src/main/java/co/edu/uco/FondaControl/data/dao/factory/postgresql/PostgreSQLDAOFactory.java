package co.edu.uco.FondaControl.data.dao.factory.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql.UsuarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.EstadoMesaDAO;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.impl.postgresql.EstadoMesaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql.IndicadorInventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql.InformeCajaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql.InventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.mesa.MesaDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql.SesionTrabajoPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLDAOFactory extends DAOFactory {

    private Connection conexion;
    private boolean transaccionEstainiciada;
    private boolean conexionEstaAbierta;

    public PostgreSQLDAOFactory() throws DataFondaControlException {
        abrirConexion();
        transaccionEstainiciada = false;
        conexionEstaAbierta = true;
    }

    @Override
    protected void abrirConexion() throws DataFondaControlException {
        var baseDatos = "fondacontrol";
        var servidor = "ep-green-snow-a4vppda9-pooler.us-east-1.aws.neon.tech";
        var usuario = "fondacontrol_owner";
        var contrasena = "npg_FOPNH4dG6UhZ"; // Reemplaza con la contraseña real
        var urlConexion = "jdbc:postgresql://" + servidor + "/" + baseDatos + "?sslmode=require";

        try {
            conexion = DriverManager.getConnection(urlConexion, usuario, contrasena);
            conexionEstaAbierta = true;
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "Error al abrir la conexión a la base de datos",
                    "SQLException al conectar a la base de datos: " + exception.getMessage(),
                    exception
            );
        } catch (Exception exception) {
            throw DataFondaControlException.reportar(
                    "Error inesperado al abrir la conexión a la base de datos",
                    "Exception al conectar a la base de datos: " + exception.getMessage(),
                    exception
            );
        }
    }

    @Override
    public void iniciarTransaccion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            conexion.setAutoCommit(false);
            transaccionEstainiciada = true;
        } catch (Exception e) {
            throw DataFondaControlException.reportar("Error al iniciar la transacción", "Detalles: " + e.getMessage(), e);
        }
    }

    @Override
    public void confirmarTransaccion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            asegurarTransaccionIniciada();
            conexion.commit();
        } catch (Exception e) {
            throw DataFondaControlException.reportar("Error al confirmar la transacción", "Detalles: " + e.getMessage(), e);
        }
    }

    @Override
    public void cancelarTransaccion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            asegurarTransaccionIniciada();
            conexion.rollback();
        } catch (Exception e) {
            throw DataFondaControlException.reportar("Error al cancelar la transacción", "Detalles: " + e.getMessage(), e);
        }
    }

    @Override
    public void cerrarConexion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            conexion.close();
        } catch (Exception e) {
            throw DataFondaControlException.reportar("Error al cerrar la conexión", "Detalles: " + e.getMessage(), e);
        }
    }

    private void asegurarTransaccionIniciada() throws DataFondaControlException {
        if (!transaccionEstainiciada) {
            throw DataFondaControlException.reportar("No se ha iniciado ninguna transacción");
        }
    }

    private void asegurarConexionAbierta() throws DataFondaControlException {
        if (!conexionEstaAbierta) {
            throw DataFondaControlException.reportar("La conexión está cerrada y no se puede continuar");
        }
    }

    @Override
    public UsuarioDAO getUsuarioDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new UsuarioPostgreSQLDAO(conexion);
    }

    @Override
    public IndicadorInventarioDAO getIndicadorInventarioDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new IndicadorInventarioPostgreSQLDAO(conexion);
    }
    @Override
    public MesaDAO getMesaDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new co.edu.uco.FondaControl.data.dao.entity.mesa.impl.postgresql.MesaPostgreSQLDAO(conexion);
    }

    @Override
    public EstadoMesaDAO getEstadoMesaDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new EstadoMesaPostgreSQLDAO(conexion);
    }


    @Override
    public InformeCajaDAO getInformeCajaDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new InformeCajaPostgreSQLDAO(conexion);
    }

    @Override
    public InventarioDAO getInventarioDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new InventarioPostgreSQLDAO(conexion);
    }

    @Override
    public SesionTrabajoDAO getSesionTrabajoDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new SesionTrabajoPostgreSQLDAO(conexion);
    }
}
