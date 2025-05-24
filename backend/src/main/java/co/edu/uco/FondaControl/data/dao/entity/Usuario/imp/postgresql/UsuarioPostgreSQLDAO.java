package co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.entity.UsuarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UsuarioPostgreSQLDAO implements UsuarioDAO {

    private final Connection connection;

    public UsuarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(UsuarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);
        var sql = "INSERT INTO usuario (nombre, contrasena, codigorol) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setString(2, entity.getContrasena());
            ps.setObject(3, entity.getCodigoRol());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                entity.setId((UUID) rs.getObject("id")); // Asignar ID generado desde la DB
            }
        }

        catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible registrar el usuario en la base de datos.",
                    "Se produjo un SQLException en 'create' de UsuarioPostgreSQLDAO. SQL=[" + sql + "], ID=[" + entity.getId() + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void update(UUID uuid, UsuarioEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        var sql = "UPDATE usuario SET nombre = ?, contrasena = ?, codigorol = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNombre());
            ps.setString(2, entity.getContrasena());
            ps.setObject(3, entity.getCodigoRol());
            ps.setObject(4, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible actualizar los datos del usuario.",
                    "SQLException en 'update' de UsuarioPostgreSQLDAO. SQL=[" + sql + "], UUID=[" + uuid + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void delete(UUID id) throws DataFondaControlException {
        if (UtilObjeto.esNulo(id)) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        var sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible eliminar el usuario.",
                    "SQLException en 'delete' de UsuarioPostgreSQLDAO. SQL=[" + sql + "], ID=[" + id + "]. Detalle: " + e.getMessage(),
                    e
            );
        }
    }


    @Override
    public UsuarioEntity findById(UUID id) throws DataFondaControlException {
        if (UtilObjeto.esNulo(id)) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        var sql = "SELECT id, nombre, contrasena, codigorol FROM usuario WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioEntity(
                            (UUID) rs.getObject("id"),
                            rs.getString("nombre"),
                            (UUID) rs.getObject("codigorol"),
                            rs.getString("contrasena")
                    );
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible obtener el usuario con el ID proporcionado.",
                    "SQLException en 'findById' de UsuarioPostgreSQLDAO. SQL=[" + sql + "], ID=[" + id + "]. Detalle: " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    private void validarEntidad(UsuarioEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad de usuario no puede ser nula.");
        }
        if (entity.getNombre() == null || entity.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo ni vacío.");
        }
        if (entity.getContrasena() == null || entity.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía.");
        }
        if (UtilObjeto.esNulo(entity.getCodigoRol())) {
            throw new IllegalArgumentException("El código de rol no puede ser nulo.");
        }
    }

}
