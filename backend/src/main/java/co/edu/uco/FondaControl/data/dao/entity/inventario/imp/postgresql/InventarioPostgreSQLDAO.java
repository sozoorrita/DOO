package co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.entity.InventarioEntity;
import co.edu.uco.FondaControl.entity.ProductoEntity;

public class InventarioPostgreSQLDAO implements InventarioDAO {

	private final Connection connection;

	public InventarioPostgreSQLDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void create(InventarioEntity entity) throws DataFondaControlException {
		validarEntidad(entity);

		var sql = new StringBuilder();
		sql.append("INSERT INTO inventario(codigo, producto, cantidad, codigoindicador) ");
		sql.append("VALUES (?, ?, ?, ?)");

		try (var preparedStatement = connection.prepareStatement(sql.toString())) {
			preparedStatement.setObject(1, entity.getCodigo());
			preparedStatement.setObject(2, entity.getProducto() != null ? entity.getProducto().getCodigo() : null);
			preparedStatement.setInt(3, entity.getCantidad());
			preparedStatement.setObject(4, entity.getCodigoIndicador());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			var msgUsuario = "No fue posible registrar el inventario.";
			var msgTecnico = "SQLException en 'create'. SQL=[" + sql + "], Detalle: " + e.getMessage();
			throw DataFondaControlException.reportar(msgUsuario, msgTecnico, e);
		}
	}

	@Override
	public List<InventarioEntity> listByFilter(InventarioEntity entity) throws DataFondaControlException {
		validarEntidad(entity);
		var resultado = new ArrayList<InventarioEntity>();
		var sql = new StringBuilder(
				"SELECT codigo, producto, cantidad, codigoindicador FROM inventario WHERE producto = ?");

		try (var ps = connection.prepareStatement(sql.toString())) {
			ps.setObject(1, entity.getProducto() != null ? entity.getProducto().getCodigo() : null);

			try (var rs = ps.executeQuery()) {
				while (rs.next()) {
					resultado.add(mapToEntity(rs));
				}
			}
		} catch (SQLException e) {
			var msgUsuario = "No fue posible filtrar inventarios.";
			var msgTecnico = "SQLException en 'listByFilter'. SQL=[" + sql + "], filtro=[producto="
					+ (entity.getProducto() != null ? entity.getProducto().getCodigo() : null) + "]";
			throw DataFondaControlException.reportar(msgUsuario, msgTecnico, e);
		}

		return resultado;
	}

	@Override
	public List<InventarioEntity> listAll() throws DataFondaControlException {
		var resultado = new ArrayList<InventarioEntity>();
		var sql = new StringBuilder("SELECT codigo, producto, cantidad, codigoindicador FROM inventario");

		try (var ps = connection.prepareStatement(sql.toString()); var rs = ps.executeQuery()) {

			while (rs.next()) {
				resultado.add(mapToEntity(rs));
			}

		} catch (SQLException e) {
			var msgUsuario = "No fue posible listar todos los inventarios.";
			var msgTecnico = "SQLException en 'listAll'. SQL=[" + sql + "]";
			throw DataFondaControlException.reportar(msgUsuario, msgTecnico, e);
		}

		return resultado;
	}

	@Override
	public List<InventarioEntity> listByCodigo(UUID uuid) throws DataFondaControlException {
		if (UtilObjeto.esNulo(uuid))
			throw new IllegalArgumentException("El UUID no puede ser nulo.");

		var resultado = new ArrayList<InventarioEntity>();
		var sql = new StringBuilder(
				"SELECT codigo, producto, cantidad, codigoindicador FROM inventario WHERE codigo = ?");

		try (var ps = connection.prepareStatement(sql.toString())) {
			ps.setObject(1, uuid);
			try (var rs = ps.executeQuery()) {
				while (rs.next()) {
					resultado.add(mapToEntity(rs));
				}
			}
		} catch (SQLException e) {
			var msgUsuario = "No fue posible obtener inventario por código.";
			var msgTecnico = "SQLException en 'listByCodigo'. SQL=[" + sql + "], código=[" + uuid + "]";
			throw DataFondaControlException.reportar(msgUsuario, msgTecnico, e);
		}

		return resultado;
	}

	@Override
	public InventarioEntity findById(UUID codigo) throws DataFondaControlException {
		if (UtilObjeto.esNulo(codigo))
			throw new IllegalArgumentException("El código no puede ser nulo.");

		var sql = new StringBuilder(
				"SELECT codigo, producto, cantidad, codigoindicador FROM inventario WHERE codigo = ?");
		InventarioEntity retorno = null;

		try (var ps = connection.prepareStatement(sql.toString())) {
			ps.setObject(1, codigo);
			try (var rs = ps.executeQuery()) {
				if (rs.next()) {
					retorno = mapToEntity(rs);
				}
			}
		} catch (SQLException e) {
			var mensageUsuario = "No fue posible encontrar inventario.";
			var mensageTecnico = "SQLException en 'findById'. SQL=[" + sql + "], código=[" + codigo + "]";
			throw DataFondaControlException.reportar(mensageUsuario, mensageTecnico, e);
		}

		return retorno;
	}

	@Override
	public void update(UUID uuid, InventarioEntity entity) throws DataFondaControlException {
		validarEntidad(entity);

		var sql = new StringBuilder(
				"UPDATE inventario SET producto = ?, cantidad = ?, codigoindicador = ? WHERE codigo = ?");

		try (var ps = connection.prepareStatement(sql.toString())) {
			ps.setObject(1, entity.getProducto() != null ? entity.getProducto().getCodigo() : null);
			ps.setInt(2, entity.getCantidad());
			ps.setObject(3, entity.getCodigoIndicador());
			ps.setObject(4, uuid);

			if (ps.executeUpdate() == 0) {
				throw DataFondaControlException.reportar("No se encontró el inventario para actualizar.",
						"No hay registro con el código=[" + uuid + "]");
			}
		} catch (SQLException e) {
			var msgUsuario = "Error al actualizar el inventario.";
			var mensajeTecnico = "SQLException en 'update'. SQL=[" + sql + "], código=[" + uuid + "]";
			throw DataFondaControlException.reportar(msgUsuario, mensajeTecnico, e);
		}
	}

	@Override
	public void createOrUpdate(InventarioEntity entity) throws DataFondaControlException {
		validarEntidad(entity);
		if (findById(entity.getCodigo()) == null) {
			create(entity);
		} else {
			update(entity.getCodigo(), entity);
		}
	}

	@Override
	public void delete(UUID codigo) throws DataFondaControlException {
		if (UtilObjeto.esNulo(codigo)) {
			throw new IllegalArgumentException("El código del inventario no puede ser nulo.");
		}
		var sql = new StringBuilder("DELETE FROM inventario WHERE codigo = ?");
		try (var ps = connection.prepareStatement(sql.toString())) {
			ps.setObject(1, codigo);
			int filas = ps.executeUpdate();
			if (filas == 0) {
				throw DataFondaControlException.reportar(
						"No se encontró el inventario para eliminar.",
						"No hay registro con el código=[" + codigo + "]"
				);
			}
		} catch (SQLException e) {
			var msgUsuario = "Error al eliminar el inventario.";
			var msgTecnico = "SQLException en 'delete'. SQL=[" + sql + "], código=[" + codigo + "]";
			throw DataFondaControlException.reportar(msgUsuario, msgTecnico, e);
		}
	}

	private InventarioEntity mapToEntity(ResultSet rs) throws SQLException {
		UUID productoId = (UUID) rs.getObject("producto");
		ProductoEntity producto = new ProductoEntity();
		producto.setCodigo(productoId);

		return new InventarioEntity((UUID) rs.getObject("codigo"), producto, rs.getInt("cantidad"),
				(UUID) rs.getObject("codigoindicador"));
	}

	private void validarEntidad(InventarioEntity entity) {
		if (UtilObjeto.esNulo(entity))
			throw new IllegalArgumentException("La entidad no puede ser nula.");
		if (UtilObjeto.esNulo(entity.getProducto()) || UtilObjeto.esNulo(entity.getProducto().getCodigo()))
			throw new IllegalArgumentException("El producto asociado no puede ser nulo.");
	}
}
