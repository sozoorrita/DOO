package co.edu.uco.FondaControl.data.dao.entity.producto.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.entity.producto.ProductoDAO;
import co.edu.uco.FondaControl.entity.ProductoEntity;
import co.edu.uco.FondaControl.entity.SubcategoriaEntity;

public class ProductoPostgreSQLDAO implements ProductoDAO {
	private final Connection conexion;

	private static final String SQL_INSERT = "INSERT INTO producto(codigo, nombre, preciolugar, preciollevar, codigosubcategoria, limitecantidad) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE producto SET nombre = ?, preciolugar = ?, preciollevar = ?, codigosubcategoria = ?, limitecantidad = ? WHERE codigo = ?";
	private static final String SQL_DELETE = "DELETE FROM producto WHERE codigo = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM producto WHERE codigo = ?";
	private static final String SQL_LIST_ALL = "SELECT * FROM producto";

	public ProductoPostgreSQLDAO(final Connection conexion) {
		this.conexion = conexion;
	}

	@Override
	public void create(final ProductoEntity entity) throws DataFondaControlException {
		try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
			ps.setObject(1, entity.getCodigo());
			ps.setString(2, entity.getNombre());
			ps.setDouble(3, entity.getPrecioLugar());
			ps.setDouble(4, entity.getPrecioLlevar());
			ps.setObject(5, entity.getSubcategoria().getCodigo());
			ps.setInt(6, entity.getLimiteCantidad());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al crear producto", "create@producto - " + e.getMessage(),
					e);
		}
	}

	@Override
	public void update(final UUID id, final ProductoEntity entity) throws DataFondaControlException {
		try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
			ps.setString(1, entity.getNombre());
			ps.setDouble(2, entity.getPrecioLugar());
			ps.setDouble(3, entity.getPrecioLlevar());
			ps.setObject(4, entity.getSubcategoria().getCodigo());
			ps.setInt(5, entity.getLimiteCantidad());
			ps.setObject(6, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al actualizar producto",
					"update@producto - " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(final UUID id) throws DataFondaControlException {
		try (PreparedStatement ps = conexion.prepareStatement(SQL_DELETE)) {
			ps.setObject(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al eliminar producto",
					"delete@producto - " + e.getMessage(), e);
		}
	}

	@Override
	public List<ProductoEntity> listAll() throws DataFondaControlException {
		List<ProductoEntity> results = new ArrayList<>();
		try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				results.add(mapRow(rs));
			}
			return results;
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al listar productos",
					"listAll@producto - " + e.getMessage(), e);
		}
	}

	@Override
	public List<ProductoEntity> listByFilter(final ProductoEntity filter) throws DataFondaControlException {
		StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
		List<Object> params = new ArrayList<>();
		if (!UtilTexto.getInstancia().esNula(filter.getNombre())) {
			sql.append(" WHERE LOWER(nombre) LIKE LOWER(?)");
			params.add("%" + filter.getNombre() + "%");
		}
		List<ProductoEntity> results = new ArrayList<>();
		try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					results.add(mapRow(rs));
				}
			}
			return results;
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al filtrar productos",
					"listByFilter@producto - " + e.getMessage(), e);
		}
	}

	@Override
	public List<ProductoEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
		ProductoEntity entity = findById(codigo);
		return entity == null ? List.of() : List.of(entity);
	}

	@Override
	public List<ProductoEntity> listBySubcategoria(final UUID subcategoriaId) throws DataFondaControlException {
		var sql = SQL_LIST_ALL + " WHERE codigosubcategoria = ?";
		List<ProductoEntity> results = new ArrayList<>();
		try (PreparedStatement ps = conexion.prepareStatement(sql)) {
			ps.setObject(1, subcategoriaId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					results.add(mapRow(rs));
				}
			}
			return results;
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al listar por subcategoría",
					"listBySubcategoria@producto - " + e.getMessage(), e);
		}
	}

	public ProductoEntity findById(final UUID id) throws DataFondaControlException {
		try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
			ps.setObject(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapRow(rs) : null;
			}
		} catch (SQLException e) {
			throw DataFondaControlException.reportar("Error al buscar producto por ID",
					"findById@producto - " + e.getMessage(), e);
		}
	}

	private ProductoEntity mapRow(final ResultSet rs) throws SQLException {
		return ProductoEntity.builder().codigo(rs.getObject("codigo", UUID.class)).nombre(rs.getString("nombre"))
				.precioLugar(rs.getDouble("preciolugar")).precioLlevar(rs.getDouble("preciollevar"))
				.subcategoria(
						SubcategoriaEntity.builder().codigo(rs.getObject("codigosubcategoria", UUID.class)).crear())
				.limiteCantidad(rs.getInt("limitecantidad")).crear();
	}
}
