package co.edu.uco.FondaControl.data.dao.entity.categoria.impl;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.entity.CategoriaEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriaPostgreSQLDAO implements CategoriaDAO {
    private final Connection conexion;

    // 1. Sentencias SQL actualizadas
    private static final String SQL_INSERT =
            "INSERT INTO categoria(codigo, nombre, fecha_creacion) VALUES (?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE categoria SET nombre = ? WHERE codigo = ? AND fecha_eliminacion IS NULL";

    private static final String SQL_SOFT_DELETE =
            "UPDATE categoria SET fecha_eliminacion = NOW() WHERE codigo = ? AND fecha_eliminacion IS NULL";

    // 3. Eliminamos el filtro "AND fecha_eliminacion IS NULL" para poder ver también filas borradas
    private static final String SQL_FIND_BY_ID =
            "SELECT codigo, nombre, fecha_creacion, fecha_eliminacion " +
                    "FROM categoria " +
                    "WHERE codigo = ?";

    // Lista todo, sin filtrar (incluye activas y eliminadas)
    private static final String SQL_LIST_ALL =
            "SELECT codigo, nombre, fecha_creacion, fecha_eliminacion FROM categoria";

    // Lista solo las activas (fecha_eliminacion IS NULL)
    private static final String SQL_LIST_ACTIVAS =
            "SELECT codigo, nombre, fecha_creacion, fecha_eliminacion FROM categoria WHERE fecha_eliminacion IS NULL";

    // Lista solo las eliminadas (fecha_eliminacion IS NOT NULL)
    private static final String SQL_LIST_ELIMINADAS =
            "SELECT codigo, nombre, fecha_creacion, fecha_eliminacion FROM categoria WHERE fecha_eliminacion IS NOT NULL";

    public CategoriaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    // 2. Métodos CRUD

    @Override
    public void create(final CategoriaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getFechaCreacion()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al crear categoría",
                    "CategoriaPostgreSQLDAO.create: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void update(final UUID id, final CategoriaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, id);
            int registros = ps.executeUpdate();
            if (registros == 0) {
                throw DataFondaControlException.reportar(
                        "No se pudo actualizar: categoría no encontrada o ya eliminada.",
                        "CategoriaPostgreSQLDAO.update: codigo=" + id,
                        null
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar categoría",
                    "CategoriaPostgreSQLDAO.update: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void delete(final UUID id) throws DataFondaControlException {
        if (id == null) {
            throw DataFondaControlException.reportar(
                    "El ID de categoría no puede ser nulo.",
                    "CategoriaPostgreSQLDAO.delete: id=null",
                    null
            );
        }
        try (PreparedStatement ps = conexion.prepareStatement(SQL_SOFT_DELETE)) {
            ps.setObject(1, id);
            int registros = ps.executeUpdate();
            if (registros == 0) {
                throw DataFondaControlException.reportar(
                        "No se pudo eliminar: categoría no encontrada o ya eliminada.",
                        "CategoriaPostgreSQLDAO.delete: codigo=" + id,
                        null
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al eliminar categoría",
                    "CategoriaPostgreSQLDAO.delete: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public CategoriaEntity findById(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al buscar categoría por ID",
                    "CategoriaPostgreSQLDAO.findById: " + e.getMessage(),
                    e
            );
        }
    }

    // 3. Métodos de listado

    @Override
    public List<CategoriaEntity> listAll() throws DataFondaControlException {
        List<CategoriaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar categorías",
                    "CategoriaPostgreSQLDAO.listAll: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<CategoriaEntity> listByFilter(final CategoriaEntity filter) throws DataFondaControlException {
        StringBuilder sql = new StringBuilder("SELECT codigo, nombre, fecha_creacion, fecha_eliminacion FROM categoria");
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (filter != null && !UtilTexto.getInstancia().esNula(filter.getNombre())) {
            sql.append(" WHERE LOWER(nombre) LIKE LOWER(?)");
            params.add("%" + filter.getNombre() + "%");
            hasWhere = true;
        }
        // Si hay más filtros en el futuro, usar hasWhere para concatenar con AND

        List<CategoriaEntity> results = new ArrayList<>();
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
                    "Error al filtrar categorías",
                    "CategoriaPostgreSQLDAO.listByFilter: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<CategoriaEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
        CategoriaEntity entidad = findById(codigo);
        return entidad == null ? List.of() : List.of(entidad);
    }

    // 4. Métodos auxiliares de mapeo

    private CategoriaEntity mapRow(final ResultSet rs) throws SQLException {
        UUID codigo = rs.getObject("codigo", UUID.class);
        String nombre = rs.getString("nombre");
        LocalDateTime fechaCreacion = rs.getTimestamp("fecha_creacion") != null
                ? rs.getTimestamp("fecha_creacion").toLocalDateTime()
                : null;
        LocalDateTime fechaEliminacion = rs.getTimestamp("fecha_eliminacion") != null
                ? rs.getTimestamp("fecha_eliminacion").toLocalDateTime()
                : null;

        return CategoriaEntity.builder()
                .codigo(codigo)
                .nombre(nombre)
                .fechaCreacion(fechaCreacion)
                .fechaEliminacion(fechaEliminacion)
                .crear();
    }

    // 5. Métodos específicos para “activas” y “eliminadas”

    public List<CategoriaEntity> listActivas() throws DataFondaControlException {
        List<CategoriaEntity> resultado = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ACTIVAS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar categorías activas",
                    "CategoriaPostgreSQLDAO.listActivas: " + e.getMessage(),
                    e
            );
        }
        return resultado;
    }

    public List<CategoriaEntity> listEliminadas() throws DataFondaControlException {
        List<CategoriaEntity> resultado = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ELIMINADAS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar categorías eliminadas",
                    "CategoriaPostgreSQLDAO.listEliminadas: " + e.getMessage(),
                    e
            );
        }
        return resultado;
    }
}
