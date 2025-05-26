package co.edu.uco.FondaControl.data.dao.entity.estadomesa.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.estadomesa.EstadoMesaDAO;
import co.edu.uco.FondaControl.entity.EstadoMesaEntity;

public class EstadoMesaPostgreSQLDAO implements EstadoMesaDAO {
    private final Connection conexion;

    private static final String SQL_INSERT =
        "INSERT INTO estadomesa(codigo, nombre) VALUES (?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE estadomesa SET nombre = ? WHERE codigo = ?";
    private static final String SQL_DELETE =
        "DELETE FROM estadomesa WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM estadomesa WHERE codigo = ?";
    private static final String SQL_LIST_ALL =
        "SELECT * FROM estadomesa";
    private static final String SQL_LIST_BY_NOMBRE =
        "SELECT * FROM estadomesa WHERE LOWER(nombre) LIKE LOWER(?)";

    public EstadoMesaPostgreSQLDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(EstadoMesaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear estado de mesa",
                "create@estadomesa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(UUID id, EstadoMesaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar estado de mesa",
                "update@estadomesa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void delete(UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_DELETE)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al eliminar estado de mesa",
                "delete@estadomesa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public EstadoMesaEntity findById(UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al buscar estado de mesa por ID",
                "findById@estadomesa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<EstadoMesaEntity> listAll() throws DataFondaControlException {
        List<EstadoMesaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar estados de mesa",
                "listAll@estadomesa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<EstadoMesaEntity> listByFilter(EstadoMesaEntity filter) throws DataFondaControlException {
        List<EstadoMesaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_BY_NOMBRE)) {
            String pattern = "%" + filter.getNombre() + "%";
            ps.setString(1, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al filtrar estados de mesa",
                "listByFilter@estadomesa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<EstadoMesaEntity> listByCodigo(UUID codigo) throws DataFondaControlException {
        EstadoMesaEntity entity = findById(codigo);
        return entity == null ? List.of() : List.of(entity);
    }

    @Override
    public List<EstadoMesaEntity> listByNombre(String nombre) throws DataFondaControlException {
        List<EstadoMesaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_BY_NOMBRE)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar estados de mesa por nombre",
                "listByNombre@estadomesa - " + e.getMessage(), e
            );
        }
    }

    private EstadoMesaEntity mapRow(ResultSet rs) throws SQLException {
        return EstadoMesaEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .nombre(rs.getString("nombre"))
            .crear();
    }
}
