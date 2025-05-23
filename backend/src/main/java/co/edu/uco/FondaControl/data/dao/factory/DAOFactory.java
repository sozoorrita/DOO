package co.edu.uco.FondaControl.data.dao.factory;


import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.data.dao.factory.postgresql.PostgreSQLDAOFactory;

public abstract class DAOFactory {
    public static DAOFactory getDAOFactory(Factory factory) throws DataFondaControlException {
        switch (factory) {
            case POSTGRESQL: {
                return new PostgreSQLDAOFactory();
            }
            default:
                var mensajeUsuario = "Se ha presentado un problema tratando de obtener la información de la fuente de datos contra la cual se llevarán a cabo las operaciones.";
                var mensajeTecnico = "Se solicitó la factoría " + factory + " pero esta no está implementada.";
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
}
