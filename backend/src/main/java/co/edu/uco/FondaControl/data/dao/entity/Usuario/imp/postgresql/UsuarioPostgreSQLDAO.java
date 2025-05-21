package co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public abstract class UsuarioPostgreSQLDAO implements UsuarioDAO {
    private final Connection connection;

    public UsuarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(UsuarioEntity entity) {
        String sql = "INSERT INTO usuario (id, nombre, contrasena, codigorol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, entity.getId());
            preparedStatement.setString(2, entity.getNombre());
            preparedStatement.setString(3, entity.getContrasena());
            preparedStatement.setObject(4, entity.getCodigoRol());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el usuario.", e);
        }
    }

    @Override
    public void delete(UsuarioEntity usuarioEntity) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, usuarioEntity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el usuario.", e);
        }
    }

    @Override
    public void update(UUID uuid, UsuarioEntity entity) {
        String sql = "UPDATE usuario SET nombre = ?, contrasena = ?, codigorol = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getNombre());
            preparedStatement.setString(2, entity.getContrasena());
            preparedStatement.setObject(3, entity.getCodigoRol());
            preparedStatement.setObject(4, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el usuario.", e);
        }
    }

    @Override
    public UsuarioEntity findById(UUID id) {
        String sql = "SELECT id, nombre, contrasena, codigorol FROM usuario WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new UsuarioEntity(
                            (UUID) resultSet.getObject("id"),
                            resultSet.getString("nombre"),
                            (UUID) resultSet.getObject("codigorol"),
                            resultSet.getString("contrasena")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el usuario por ID.", e);
        }
        return null;
    }
}