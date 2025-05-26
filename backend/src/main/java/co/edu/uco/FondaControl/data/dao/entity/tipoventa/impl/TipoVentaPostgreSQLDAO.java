package co.edu.uco.FondaControl.data.dao.entity.tipoventa.impl;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.tipoventa.TipoVentaDAO;
import co.edu.uco.FondaControl.entity.TipoVentaEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TipoVentaPostgreSQLDAO implements TipoVentaDAO {
    private final Connection conexion;

    private static final String SQL_INSERT =
        "INSERT INTO tipoventa(codigo, nombre) VALUES (?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE tipoventa SET nombre = ? WHERE codigo = ?";
    private static final String SQL_DELETE =
        "DELETE FROM tipoventa WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM tipoventa WHERE codigo = ?";
    private static final String SQL_LIST_ALL =
        "SELECT * FROM tipoventa";

    public TipoVentaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final TipoVentaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear tipo de venta",
                "create@tipoventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(final UUID id, final TipoVentaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar tipo de venta",
                "update@tipoventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void delete(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_DELETE)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al eliminar tipo de venta",
                "delete@tipoventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public TipoVentaEntity findById(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al buscar tipo de venta por ID",
                "findById@tipoventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<TipoVentaEntity> listAll() throws DataFondaControlException {
        List<TipoVentaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar tipos de venta",
                "listAll@tipoventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<TipoVentaEntity> listByFilter(final TipoVentaEntity filter) throws DataFondaControlException {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        List<Object> params = new ArrayList<>();
        if (!UtilTexto.getInstancia().esNula(filter.getNombre())) {
            sql.append(" WHERE LOWER(nombre) LIKE LOWER(?)");
            params.add("%" + filter.getNombre() + "%");
        }
        List<TipoVentaEntity> results = new ArrayList<>();
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
            throw DataFondaControlException.reportar(
                "Error al filtrar tipos de venta",
                "listByFilter@tipoventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<TipoVentaEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
        TipoVentaEntity entity = findById(codigo);
        return entity == null ? List.of() : List.of(entity);
    }

    private TipoVentaEntity mapRow(final ResultSet rs) throws SQLException {
        return TipoVentaEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .nombre(rs.getString("nombre"))
            .crear();
    }

	@Override
	public List<TipoVentaEntity> listByNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
}
