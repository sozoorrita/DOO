package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql;


import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        var sql = "INSERT INTO indicador_inventario (codigo, nombre) VALUES (?, ?)";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al crear el indicador de inventario.",
                    "SQLException en create(): " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<IndicadorInventarioEntity> listByFilter(IndicadorInventarioEntity entity) throws DataFondaControlException {
        if (entity == null || entity.getNombre() == null || entity.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni exceder los 50 caracteres.");
        }

        var sql = "SELECT codigo, nombre FROM indicador_inventario WHERE nombre LIKE ?";
        var result = new ArrayList<IndicadorInventarioEntity>();
        try (var ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + entity.getNombre() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new IndicadorInventarioEntity(
                            (UUID) rs.getObject("codigo"),
                            rs.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar indicadores de inventario por filtro.",
                    "SQLException en listByFilter(): " + e.getMessage(),
                    e
            );
        }
        return result;
    }

    @Override
    public List<IndicadorInventarioEntity> listAll() throws DataFondaControlException {
        var sql = "SELECT codigo, nombre FROM indicador_inventario";
        var result = new ArrayList<IndicadorInventarioEntity>();

        try (var ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new IndicadorInventarioEntity(
                        (UUID) rs.getObject("codigo"),
                        rs.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar todos los indicadores de inventario.",
                    "SQLException en listAll(): " + e.getMessage(),
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

        var sql = "SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?";
        var result = new ArrayList<IndicadorInventarioEntity>();

        try (var ps = connection.prepareStatement(sql)) {
            ps.setObject(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new IndicadorInventarioEntity(
                            (UUID) rs.getObject("codigo"),
                            rs.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar el indicador de inventario por código.",
                    "SQLException en listByCodigo(): " + e.getMessage(),
                    e
            );
        }

        return result;
    }

    @Override
    public void update(UUID uuid, IndicadorInventarioEntity entity) throws DataFondaControlException {
        if (uuid == null) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        var sql = "UPDATE indicador_inventario SET nombre = ? WHERE codigo = ?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, uuid);
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el indicador para actualizar.",
                        "No hay registro con el código: " + uuid
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar el indicador de inventario.",
                    "SQLException en update(): " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public IndicadorInventarioEntity findById(UUID codigo) throws DataFondaControlException {
        if (codigo == null) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        var sql = "SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setObject(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new IndicadorInventarioEntity(
                            (UUID) rs.getObject("codigo"),
                            rs.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al buscar el indicador por ID.",
                    "SQLException en findById(): " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    @Override
    public void update(List<IndicadorInventarioEntity> entities) throws DataFondaControlException {
        if (entities == null || entities.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede ser nula ni vacía.");
        }

        var sql = "UPDATE indicador_inventario SET nombre = ? WHERE codigo = ?";
        try {
            for (IndicadorInventarioEntity entity : entities) {
                validarEntidad(entity);
                try (var ps = connection.prepareStatement(sql)) {
                    ps.setString(1, entity.getNombre());
                    ps.setObject(2, entity.getCodigo());
                    int filas = ps.executeUpdate();
                    if (filas == 0) {
                        throw DataFondaControlException.reportar(
                                "No se encontró un indicador de inventario para actualizar.",
                                "Código no encontrado: " + entity.getCodigo()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar la lista de indicadores de inventario.",
                    "SQLException en update(List): " + e.getMessage(),
                    e
            );
        }
    }

    private void validarEntidad(IndicadorInventarioEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (entity.getNombre() == null || entity.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        if (entity.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede exceder los 50 caracteres.");
        }
    }
}
