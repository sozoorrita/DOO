package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IndicadorInventarioPostgreSQLDAO implements IndicadorInventarioDAO {

    private final Connection connection;

    public IndicadorInventarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(IndicadorInventarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);
        final String sql = "INSERT INTO indicador_inventario (codigo, nombre) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible registrar el indicador de inventario.",
                    "SQLException en 'create' con SQL=[" + sql + "], código=[" + entity.getCodigo() + "], nombre=[" + entity.getNombre() + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void delete(UUID id) throws DataFondaControlException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        final String sql = "DELETE FROM indicador_inventario WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible eliminar el indicador de inventario.",
                    "SQLException en 'delete' con SQL=[" + sql + "], ID=[" + id + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<IndicadorInventarioEntity> listByFilter(IndicadorInventarioEntity entity) throws DataFondaControlException {
        if (entity == null || UtilTexto.getInstancia().esNula(entity.getNombre()) || entity.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni exceder los 50 caracteres.");
        }

        final String sql = "SELECT codigo, nombre FROM indicador_inventario WHERE nombre LIKE ?";
        List<IndicadorInventarioEntity> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + entity.getNombre() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapToEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible filtrar los indicadores de inventario.",
                    "SQLException en 'listByFilter' con SQL=[" + sql + "], nombre filtro=[" + entity.getNombre() + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
        return result;
    }

    @Override
    public List<IndicadorInventarioEntity> listAll() throws DataFondaControlException {
        final String sql = "SELECT codigo, nombre FROM indicador_inventario";
        List<IndicadorInventarioEntity> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible listar todos los indicadores de inventario.",
                    "SQLException en 'listAll' con SQL=[" + sql + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
        return result;
    }

    @Override
    public List<IndicadorInventarioEntity> listByCodigo(UUID uuid) throws DataFondaControlException {
        if (uuid == null) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }

        final String sql = "SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?";
        List<IndicadorInventarioEntity> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapToEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible obtener el indicador de inventario por código.",
                    "SQLException en 'listByCodigo' con SQL=[" + sql + "], código=[" + uuid + "]. Detalle: " + e.getMessage(),
                    e
            );
        }

        return result;
    }

    @Override
    public IndicadorInventarioEntity findById(UUID codigo) throws DataFondaControlException {
        if (codigo == null) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        final String sql = "SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToEntity(rs);
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible encontrar el indicador de inventario por ID.",
                    "SQLException en 'findById' con SQL=[" + sql + "], ID=[" + codigo + "]. Detalle: " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    @Override
    public void update(UUID uuid, IndicadorInventarioEntity entity) throws DataFondaControlException {
        if (uuid == null) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        final String sql = "UPDATE indicador_inventario SET nombre = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, uuid);
            if (ps.executeUpdate() == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el indicador para actualizar.",
                        "No hay registro con el código=[" + uuid + "] en la base de datos."
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar el indicador de inventario.",
                    "SQLException en 'update' con SQL=[" + sql + "], código=[" + uuid + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void update(List<IndicadorInventarioEntity> entities) throws DataFondaControlException {
        if (entities == null || entities.isEmpty()) {
            throw new IllegalArgumentException("La lista de entidades no puede ser nula ni vacía.");
        }

        final String sql = "UPDATE indicador_inventario SET nombre = ? WHERE codigo = ?";
        try {
            for (IndicadorInventarioEntity entity : entities) {
                validarEntidad(entity);
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, entity.getNombre());
                    ps.setObject(2, entity.getCodigo());
                    if (ps.executeUpdate() == 0) {
                        throw DataFondaControlException.reportar(
                                "No se encontró un indicador de inventario para actualizar.",
                                "Código no encontrado en 'update(List)': " + entity.getCodigo()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar múltiples indicadores de inventario.",
                    "SQLException en 'update(List)' con SQL=[" + sql + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }

    private IndicadorInventarioEntity mapToEntity(ResultSet rs) throws SQLException {
        return new IndicadorInventarioEntity(
                (UUID) rs.getObject("codigo"),
                rs.getString("nombre")
        );
    }

    private void validarEntidad(IndicadorInventarioEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (UtilTexto.getInstancia().esNula(entity.getNombre())) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni estar vacío.");
        }
        if (entity.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre del indicador no puede exceder los 50 caracteres.");
        }
    }
}
