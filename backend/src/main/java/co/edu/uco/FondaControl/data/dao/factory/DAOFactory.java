package co.edu.uco.FondaControl.data.dao.factory;

import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.factory.postgresql.PostgreSQLDAOFactory;

public abstract class DAOFactory {
    public static DAOFactory getDAOFactory(Factory factory) throws FondaControlException {
        switch (factory) {
            case POSTGRESQL: {
                return new PostgreSQLDAOFactory();
            }
            default:
                throw new IllegalArgumentException("unexpected value: " + factory);
        }
    }

    protected abstract void abrirConexion() throws FondaControlException;
    public abstract void iniciarTransaccion() throws FondaControlException;
    public abstract void confirmarTransaccion() throws FondaControlException;
    public abstract void cancelarTransaccion() throws FondaControlException;
    public abstract void cerrarConexion() throws FondaControlException;

    public abstract IndicadorInventarioDAO getIndicadorInventarioDAO();
    public abstract InformeCajaDAO getInformeCajaDAO();
    public abstract InventarioDAO getInventarioDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract SesionTrabajoDAO getSesionTrabajoDAO();
}
