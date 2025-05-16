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

import java.sql.Connection;

public class PostgreSQLDAOFactory extends DAOFactory {

    private Connection conexion;
    public PostgreSQLDAOFactory() {
        abrirConexion();
    }

    @Override
    protected void abrirConexion() {
        conexion = null;

    }

    @Override
    public void iniciarTransaccion() {

    }

    @Override
    public void confirmarTransaccion() {

    }

    @Override
    public void cancelarTransaccion() {

    }

    @Override
    public void cerrarConexion() {

    }

    @Override
    public IndicadorInventarioDAO getIndicadorInventarioDAO() {
        return new IndicadorInventarioPostgreSQLDAO(conexion);
    }

    @Override
    public InformeCajaDAO getInformeCajaDAO() {
        return new InformeCajaPostgreSQLDAO(conexion);
    }

    @Override
    public InventarioDAO getInventarioDAO() {
        return new InventarioPostgreSQLDAO(conexion);
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new UsuarioPostgreSQLDAO(conexion);
    }

    @Override
    public SesionTrabajoDAO getSesionTrabajoDAO() {
        return new SesionTrabajoPostgreSQLDAO(conexion);
    }
}
