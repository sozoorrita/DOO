package co.edu.uco.FondaControl.data.dao.factory.postgresql;


import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql.UsuarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql.IndicadorInventarioPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql.InformeCajaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql.InventarioPostgreSQLDAO;
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
        conexionEstaAbierta = false;
    }

    @Override
    protected void abrirConexion() throws DataFondaControlException {
        var baseDatos = "fondacontroldb";
        var servidor = "9-pooler.us-east-1.aws.neon.tech";
        var urlConexion = "jdbc:postgresql://" + servidor + "/" + baseDatos;
        var usuario = "tu_usuario";
        var contrasena = "tu_contrasena";

        try {
            conexion = DriverManager.getConnection(urlConexion, usuario, contrasena);
            conexionEstaAbierta = true;
        } catch (SQLException exception) {
            var mensajeUsuario = "Error al abrir la conexión a la base de datos";
            var mensajeTecnico = "Se presentó una excepción de tipo SQLException tratando de obtener la conexión con la base de datos "
                    + baseDatos + " en el servidor " + servidor + ". Para más detalles, consulte el log de errores.";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);

        } catch (Exception exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado al abrir la conexión a la base de datos";
            var mensajeTecnico = "Se presentó una excepción de tipo Exception tratando de obtener la conexión con la base de datos "
                    + baseDatos + " en el servidor " + servidor + ". Para más detalles, consulte el log de errores.";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    @Override
    public void iniciarTransaccion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            conexion.setAutoCommit(false);
            transaccionEstainiciada = true;
        } catch (SQLException exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de iniciar la transacción con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción de tipo SQLException tratando de iniciar la transacción sobre la conexión con la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } catch (Exception exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de iniciar la transacción con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción no controlada de tipo Exception tratando de iniciar la transacción sobre la conexión con la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    @Override
    public void confirmarTransaccion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            asegurarTransaccionIniciada();
            conexion.commit();
        } catch (SQLException exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de confirmar la transacción con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción de tipo SQLException tratando de confirmar la transacción sobre la conexión con la base de datos";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } catch (Exception exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de confirmar la transacción con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción no controlada de tipo Exception tratando de confirmar la transacción sobre la conexión con la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    @Override
    public void cancelarTransaccion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            asegurarTransaccionIniciada();
            conexion.rollback();
        } catch (SQLException exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de cancelar la transacción con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción de tipo SQLException tratando de cancelar la transacción sobre la conexión con la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } catch (Exception exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de cancelar la transacción con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción no controlada de tipo Exception tratando de cancelar la transacción sobre la conexión con la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    @Override
    public void cerrarConexion() throws DataFondaControlException {
        try {
            asegurarConexionAbierta();
            conexion.close();
        } catch (SQLException exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de cerrar la conexión con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción de tipo SQLException tratando de cerrar la conexión sobre la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        } catch (Exception exception) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de cerrar la conexión con la fuente de datos";
            var mensajeTecnico = "Se presentó una excepción no controlada de tipo Exception tratando de cerrar la conexión sobre la base de datos";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }
    }

    private void asegurarTransaccionIniciada() throws DataFondaControlException {
        if (!transaccionEstainiciada) {
            var mensajeUsuario = "Se ha presentado un error inesperado tratando de gestionar la transacción con la fuente de datos";
            var mensajeTecnico = "Se intentó gestionar (COMMIT/ROLLBACK) una transacción que no ha sido iniciada";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico);
        }
    }

    private void asegurarConexionAbierta() throws DataFondaControlException {
        if (!conexionEstaAbierta) {
            var mensajeUsuario = "Se ha presentado un problema tratando de llevar a cabo la operación deseada con una conexión cerrada";
            var mensajeTecnico = "Se intentó llevar a cabo una operación que requería una conexión abierta, pero la conexión estaba cerrada";

            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico);
        }
    }

    @Override
    public IndicadorInventarioDAO getIndicadorInventarioDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new IndicadorInventarioPostgreSQLDAO(conexion);
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
    public UsuarioDAO getUsuarioDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new UsuarioPostgreSQLDAO(conexion);
    }

    @Override
    public SesionTrabajoDAO getSesionTrabajoDAO() throws DataFondaControlException {
        asegurarConexionAbierta();
        return new SesionTrabajoPostgreSQLDAO(conexion);
    }
}
