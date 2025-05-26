package co.edu.uco.FondaControl.data.dao.factory.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql.UsuarioPostgreSQLDAO;
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
import co.edu.uco.FondaControl.data.dao.entity.venta.VentaDAO;
import co.edu.uco.FondaControl.data.dao.entity.venta.impl.VentaPostgreSQLDAO;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

public final class PostgreSQLDAOFactory extends DAOFactory {
	private Connection conexion;
	private boolean transaccionIniciada;
	private boolean conexionAbierta;

	public PostgreSQLDAOFactory() throws DataFondaControlException {
		abrirConexion();
		this.transaccionIniciada = false;
		this.conexionAbierta = true;
	}

	@Override
	protected void abrirConexion() throws DataFondaControlException {
		var servidor = "ep-green-snow-a4vppda9-pooler.us-east-1.aws.neon.tech";
		var baseDatos = "fondacontrol";
		var usuario = "fondacontrol_owner";
		var contrasena = "npg_FOPNH4dG6UhZ";
		var url = String.format("jdbc:postgresql://%s/%s?sslmode=require", servidor, baseDatos);

		try {
			conexion = DriverManager.getConnection(url, usuario, contrasena);
			conexionAbierta = true;
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al abrir la conexión a la base de datos",
					"abrirConexion(): " + e.getMessage(), e);
		}
	}

	@Override
	public void iniciarTransaccion() throws DataFondaControlException {
		try {
			validarConexion();
			conexion.setAutoCommit(false);
			transaccionIniciada = true;
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al iniciar transacción",
					"iniciarTransaccion(): " + e.getMessage(), e);
		}
	}

	@Override
	public void confirmarTransaccion() throws DataFondaControlException {
		try {
			validarConexion();
			validarTransaccion();
			conexion.commit();
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al confirmar transacción",
					"confirmarTransaccion(): " + e.getMessage(), e);
		}
	}

	@Override
	public void cancelarTransaccion() throws DataFondaControlException {
		try {
			validarConexion();
			validarTransaccion();
			conexion.rollback();
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al cancelar transacción",
					"cancelarTransaccion(): " + e.getMessage(), e);
		}
	}

	@Override
	public void cerrarConexion() throws DataFondaControlException {
		try {
			validarConexion();
			conexion.close();
			conexionAbierta = false;
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al cerrar la conexión",
					"cerrarConexion(): " + e.getMessage(), e);
		}
	}

	private void validarConexion() throws DataFondaControlException {
		if (!conexionAbierta) {
			throw DataFondaControlException.reportar("Conexión cerrada",
					"La conexión debe estar abierta para esta operación");
		}
	}

	private void validarTransaccion() throws DataFondaControlException {
		if (!transaccionIniciada) {
			throw DataFondaControlException.reportar("Transacción no iniciada",
					"Se debe iniciar una transacción antes de esta operación");
		}
	}

	@Override
	public UsuarioDAO getUsuarioDAO() throws DataFondaControlException {
		validarConexion();
		return new UsuarioPostgreSQLDAO(conexion);
	}

	@Override
	public IndicadorInventarioDAO getIndicadorInventarioDAO() throws DataFondaControlException {
		validarConexion();
		return new IndicadorInventarioPostgreSQLDAO(conexion);
	}

	@Override
	public SesionTrabajoDAO getSesionTrabajoDAO() throws DataFondaControlException {
		validarConexion();
		return new SesionTrabajoPostgreSQLDAO(conexion);
	}

	@Override
	public MesaDAO getMesaDAO() throws DataFondaControlException {
		validarConexion();
		return new MesaPostgreSQLDAO(conexion);
	}

	@Override
	public EstadoMesaDAO getEstadoMesaDAO() throws DataFondaControlException {
		validarConexion();
		return new EstadoMesaPostgreSQLDAO(conexion);
	}

	@Override
	public InformeCajaDAO getInformeCajaDAO() throws DataFondaControlException {
		validarConexion();
		return new InformeCajaPostgreSQLDAO(conexion);
	}

	@Override
	public InventarioDAO getInventarioDAO() throws DataFondaControlException {
		validarConexion();
		return new InventarioPostgreSQLDAO(conexion);
	}

	@Override
	public CategoriaDAO getCategoriaDAO() throws DataFondaControlException {
		validarConexion();
		return new CategoriaPostgreSQLDAO(conexion);
	}

	@Override
	public SubcategoriaDAO getSubcategoriaDAO() throws DataFondaControlException {
		validarConexion();
		return new SubcategoriaPostgreSQLDAO(conexion);
	}

	@Override
	public TipoVentaDAO getTipoVentaDAO() throws DataFondaControlException {
		validarConexion();
		return new TipoVentaPostgreSQLDAO(conexion);
	}

	@Override
	public FormaPagoDAO getFormaPagoDAO() throws DataFondaControlException {
		validarConexion();
		return new FormaPagoPostgreSQLDAO(conexion);
	}

	@Override
	public RolDAO getRolDAO() throws DataFondaControlException {
		validarConexion();
		return new RolPostgreSQLDAO(conexion);
	}

	@Override
	public ProductoDAO getProductoDAO() throws DataFondaControlException {
		validarConexion();
		return new ProductoPostgreSQLDAO(conexion);
	}

	@Override
	public DetalleVentaDAO getDetalleVentaDAO() throws DataFondaControlException {
		validarConexion();
		return new DetalleVentaPostgreSQLDAO(conexion);
	}

	@Override
	public VentaDAO getVentaDAO() throws DataFondaControlException {
		validarConexion();
		return new VentaPostgreSQLDAO(conexion);
	}
}
