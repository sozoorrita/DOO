package co.edu.uco.FondaControl.data.dao.factory.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

public class PostgreSQLDAOFactory extends DAOFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/DOO2025FONDACONTROL";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1036778928";
    private static final String DRIVER = "org.postgresql.Driver";

    private Connection conexion;
    private boolean transaccionEstaIniciada;
    private boolean conexionEstaAbierta;

    public PostgreSQLDAOFactory() throws FondaControlException {
        abrirConexion();
        transaccionEstaIniciada = false;
    }

    @Override
    protected void abrirConexion() throws FondaControlException {
        var baseDatos = "DOO2025FONDACONTROL";
        var servidor = "localhost";

        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            conexionEstaAbierta = true;
        } catch (SQLException exception) {
            var mensajeUsuario = "Se ha presentado un problema tratando de obtener la conexión con la fuente de datos para llevar a cabo la operación deseada.";
            var mensajeTecnico = "Se presentó una SQLException tratando de conectar con la base de datos " + baseDatos
                    + " en el servidor " + servidor + ".";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } catch (Exception exception) {
            var mensajeUsuario = "Se ha presentado un problema inesperado tratando de obtener la conexión.";
            var mensajeTecnico = "Excepción NO CONTROLADA tratando de conectar con la base de datos " + baseDatos
                    + " en el servidor " + servidor + ".";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    @Override
    public void iniciarTransaccion() throws FondaControlException {
        try {
            asegurarConexionAbierta();
            conexion.setAutoCommit(false);
            transaccionEstaIniciada = true;
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar("Error al iniciar transacción.",
                    "SQLException al desactivar AutoCommit.", exception);
        }
    }

    @Override
    public void confirmarTransaccion() throws FondaControlException {
        try {
            asegurarConexionAbierta();
            asegurarTransaccionIniciada();
            conexion.commit();
            transaccionEstaIniciada = false;
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar("Error al confirmar transacción.", "SQLException en commit().",
                    exception);
        }
    }

    @Override
    public void cancelarTransaccion() throws FondaControlException {
        try {
            asegurarConexionAbierta();
            asegurarTransaccionIniciada();
            conexion.rollback();
            transaccionEstaIniciada = false;
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar("Error al cancelar transacción.", "SQLException en rollback().",
                    exception);
        }
    }

    @Override
    public void cerrarConexion() throws FondaControlException {
        try {
            asegurarConexionAbierta();
            conexion.close();
            conexionEstaAbierta = false;
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar("Error al cerrar conexión.", "SQLException en close().",
                    exception);
        }
    }

    private void asegurarTransaccionIniciada() throws FondaControlException {
        if (!transaccionEstaIniciada) {
            throw DataFondaControlException.reportar("Transacción no iniciada.",
                    "Se intentó confirmar o cancelar una transacción que no ha sido iniciada.");
        }
    }

    private void asegurarConexionAbierta() throws FondaControlException {
        if (!conexionEstaAbierta || conexion == null) {
            throw DataFondaControlException.reportar("Conexión no abierta.",
                    "Se intentó operar con una conexión cerrada o nula.");
        }
    }

    @Override
    public IndicadorInventarioDAO getIndicadorInventarioDAO() {
        // Pendiente de implementación
        return null;
    }

    @Override
    public InformeCajaDAO getInformeCajaDAO() {
        // Pendiente de implementación
        return null;
    }

    @Override
    public InventarioDAO getInventarioDAO() {
        // Pendiente de implementación
        return null;
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        // Pendiente de implementación
        return null;
    }

    @Override
    public SesionTrabajoDAO getSesionTrabajoDAO() {
        // Pendiente de implementación
        return null;
    }
}
