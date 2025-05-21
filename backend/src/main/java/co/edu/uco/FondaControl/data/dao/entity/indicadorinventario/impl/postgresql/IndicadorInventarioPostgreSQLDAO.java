package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class IndicadorInventarioPostgreSQLDAO implements IndicadorInventarioDAO {

    private final Connection connection;

    public IndicadorInventarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(IndicadorInventarioEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }

        String sql = "INSERT INTO indicador_inventario (codigo, nombre) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, entity.getCodigo());
            preparedStatement.setString(2, entity.getNombre());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el indicador de inventario.", e);
        }
    }

    @Override
    public List<IndicadorInventarioEntity> listByFilter(IndicadorInventarioEntity entity) {
        List<IndicadorInventarioEntity> result = new ArrayList<>();
        String sql = "SELECT codigo, nombre FROM indicador_inventario WHERE nombre LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + entity.getNombre() + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new IndicadorInventarioEntity(
                            (UUID) resultSet.getObject("codigo"),
                            resultSet.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los indicadores de inventario por filtro.", e);
        }
        return result;
    }

    @Override
    public List<IndicadorInventarioEntity> listAll() {
        List<IndicadorInventarioEntity> result = new ArrayList<>();
        String sql = "SELECT codigo, nombre FROM indicador_inventario";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(new IndicadorInventarioEntity(
                        (UUID) resultSet.getObject("codigo"),
                        resultSet.getString("nombre")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todos los indicadores de inventario.", e);
        }
        return result;
    }

    @Override
    public List<IndicadorInventarioEntity> listByCodigo(UUID uuid) {
        List<IndicadorInventarioEntity> result = new ArrayList<>();
        String sql = "SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, uuid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(new IndicadorInventarioEntity(
                            (UUID) resultSet.getObject("codigo"),
                            resultSet.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los indicadores de inventario por código.", e);
        }
        return result;
    }

    @Override
    public void update(UUID uuid, IndicadorInventarioEntity entity) {
        if (uuid == null || entity == null) {
            throw new IllegalArgumentException("El UUID y la entidad no pueden ser nulos.");
        }

        String sql = "UPDATE indicador_inventario SET nombre = ? WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getNombre());
            preparedStatement.setObject(2, uuid);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No se encontró el registro con el código especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el indicador de inventario.", e);
        }
    }

    @Override
    public IndicadorInventarioEntity findById(UUID codigo) {
        String sql = "SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, codigo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new IndicadorInventarioEntity(
                            (UUID) resultSet.getObject("codigo"),
                            resultSet.getString("nombre")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el indicador de inventario por ID.", e);
        }
        return null;
    }

    @Override
    public void update(List<IndicadorInventarioEntity> entities) {

    }
}