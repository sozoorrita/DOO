package co.edu.uco.FondaControl.data.dao.entity.rol.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.rol.RolDAO;
import co.edu.uco.FondaControl.entity.RolEntity;

public class RolPostgreSQLDAO implements RolDAO {
    private final Connection conexion;

    private static final String SQL_INSERT =
        "INSERT INTO rol(codigo, nombre) VALUES (?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE rol SET nombre = ? WHERE codigo = ?";
    private static final String SQL_DELETE =
        "DELETE FROM rol WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM rol WHERE codigo = ?";
    private static final String SQL_LIST_ALL =
        "SELECT * FROM rol";

    public RolPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final RolEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear rol",
                "create@rol - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(final UUID id, final RolEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar rol",
                "update@rol - " + e.getMessage(), e
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
                "Error al eliminar rol",
                "delete@rol - " + e.getMessage(), e
            );
        }
    }

    @Override
    public RolEntity findById(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al buscar rol por ID",
                "findById@rol - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<RolEntity> listAll() throws DataFondaControlException {
        List<RolEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar roles",
                "listAll@rol - " + e.getMessage(), e
            );
        }
    }

    private RolEntity mapRow(final ResultSet rs) throws SQLException {
        return RolEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .nombre(rs.getString("nombre"))
            .crear();
    }

	@Override
	public List<RolEntity> listByFilter(RolEntity entity) throws DataFondaControlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RolEntity> listByCodigo(UUID codigo) throws DataFondaControlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RolEntity> listByNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
}
