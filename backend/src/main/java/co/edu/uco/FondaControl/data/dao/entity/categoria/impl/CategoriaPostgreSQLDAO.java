package co.edu.uco.FondaControl.data.dao.entity.categoria.impl;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.categoria.CategoriaDAO;
import co.edu.uco.FondaControl.entity.CategoriaEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriaPostgreSQLDAO implements CategoriaDAO {
    private final Connection conexion;

    private static final String SQL_INSERT = "INSERT INTO categoria(codigo, nombre) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE categoria SET nombre = ? WHERE codigo = ?";
    private static final String SQL_DELETE = "DELETE FROM categoria WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM categoria WHERE codigo = ?";
    private static final String SQL_LIST_ALL = "SELECT * FROM categoria";

    public CategoriaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final CategoriaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear categoría",
                "create@categoria - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(final UUID id, final CategoriaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar categoría",
                "update@categoria - " + e.getMessage(), e
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
                "Error al eliminar categoría",
                "delete@categoria - " + e.getMessage(), e
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
                "findById@categoria - " + e.getMessage(), e
            );
        }
    }

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
                "Error al listar todas las categorías",
                "listAll@categoria - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<CategoriaEntity> listByFilter(final CategoriaEntity filter) throws DataFondaControlException {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        List<Object> params = new ArrayList<>();
        if (!UtilTexto.getInstancia().esNula(filter.getNombre())) {
            sql.append(" WHERE LOWER(nombre) LIKE LOWER(?)");
            params.add("%" + filter.getNombre() + "%");
        }
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
                "listByFilter@categoria - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<CategoriaEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
        return List.of(findById(codigo));
    }

    private CategoriaEntity mapRow(final ResultSet rs) throws SQLException {
        return CategoriaEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .nombre(rs.getString("nombre"))
            .crear();
    }
}
