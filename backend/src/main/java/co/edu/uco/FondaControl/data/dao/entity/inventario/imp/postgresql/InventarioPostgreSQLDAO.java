package co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.entity.InventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventarioPostgreSQLDAO implements InventarioDAO {
    private final Connection connection;

    public InventarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(InventarioEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }

        String sql = "INSERT INTO inventario (codigo, nombreproducto, cantidad, codigoindicador) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, entity.getCodigo());
            preparedStatement.setString(2, entity.getNombreProducto());
            preparedStatement.setInt(3, entity.getCantidad());
            preparedStatement.setObject(4, entity.getCodigoIndicador());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el inventario.", e);
        }
    }

    @Override
    public List<InventarioEntity> listByFilter(InventarioEntity entity) {
        List<InventarioEntity> result = new ArrayList<>();
        String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario WHERE nombreproducto LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + entity.getNombreProducto() + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los inventarios por filtro.", e);
        }
        return result;
    }

    @Override
    public List<InventarioEntity> listAll() {
        List<InventarioEntity> result = new ArrayList<>();
        String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapToEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todos los inventarios.", e);
        }
        return result;
    }

    @Override
    public List<InventarioEntity> listByCodigo(UUID uuid) {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }

        List<InventarioEntity> result = new ArrayList<>();
        String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, uuid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(mapToEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los inventarios por código.", e);
        }
        return result;
    }

    @Override
    public void update(UUID uuid, InventarioEntity entity) {
        if (UtilObjeto.esNulo(uuid) || UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("El UUID y la entidad no pueden ser nulos.");
        }

        String sql = "UPDATE inventario SET nombreproducto = ?, cantidad = ?, codigoindicador = ? WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getNombreProducto());
            preparedStatement.setInt(2, entity.getCantidad());
            preparedStatement.setObject(3, entity.getCodigoIndicador());
            preparedStatement.setObject(4, uuid);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No se encontró el registro con el código especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el inventario.", e);
        }
    }

    @Override
    public InventarioEntity findById(UUID codigo) {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, codigo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el inventario por ID.", e);
        }
        return null;
    }

    @Override
    public void createOrUpdate(InventarioEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }

        if (findById(entity.getCodigo()) == null) {
            create(entity);
        } else {
            update(entity.getCodigo(), entity);
        }
    }

    private InventarioEntity mapToEntity(ResultSet resultSet) throws SQLException {
        return new InventarioEntity(
                (UUID) resultSet.getObject("codigo"),
                resultSet.getString("nombreproducto"),
                resultSet.getInt("cantidad"),
                (UUID) resultSet.getObject("codigoindicador")
        );
    }

    @Override
    public void update(InventarioEntity inventarioEntity, UUID entity) {

    }
}