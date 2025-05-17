package co.edu.uco.FondaControl.data.dao.factory;

import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.factory.postgresql.PostgreSQLDAOFactory;

public abstract class DAOFactory {
    public static DAOFactory getDAOFactory(Factory factory){
        switch (factory) {
            case POSTGRESQL: {
                return new PostgreSQLDAOFactory();
            }
            default:
                throw new IllegalArgumentException("unexpected value: " + factory);
        }
    }



    protected abstract void abrirConexion();
    public abstract  void iniciarTransaccion();
    public abstract void confirmarTransaccion();
    public abstract void cancelarTransaccion();
    public abstract void cerrarConexion();

    public abstract IndicadorInventarioDAO getIndicadorInventarioDAO();
    public abstract InformeCajaDAO getInformeCajaDAO();
    public abstract InventarioDAO getInventarioDAO();
    public abstract UsuarioDAO getUsuarioDAO();
    public abstract SesionTrabajoDAO getSesionTrabajoDAO();
    }

