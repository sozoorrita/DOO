package co.edu.uco.FondaControl.data.dao.entity.Usuario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.Usuario.UsuarioDAO;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementación PostgreSQL mínima de UsuarioDAO, sin lógica de bloqueo ni campos adicionales.
 * Provee: create, update, delete, findById, listByCodigo, listByFilter, listAll.
 */
public class UsuarioPostgreSQLDAO implements UsuarioDAO {

    private final Connection conexion;

    public UsuarioPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final UsuarioEntity entity) throws DataFondaControlException {
        validarEntidadParaInsertYUpdate(entity);

        final var sql = new StringBuilder(
                "INSERT INTO usuario (id, nombre, contrasena, codigorol) VALUES (?, ?, ?, ?)"
        );

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, entity.getId());
            sentencia.setString(2, entity.getNombre().trim());
            sentencia.setString(3, entity.getContrasena());
            sentencia.setObject(4, entity.getCodigoRol());

            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible registrar el usuario en la base de datos.",
                    "SQLException en create de UsuarioPostgreSQLDAO. SQL=[" + sql + "], ID=[" + entity.getId() + "]",
                    excepcion
            );
        }
    }

    @Override
    public void update(final UUID uuid, final UsuarioEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidadParaInsertYUpdate(entity);

        final var sql = new StringBuilder(
                "UPDATE usuario SET nombre = ?, contrasena = ?, codigorol = ? WHERE id = ?"
        );

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setString(1, entity.getNombre().trim());
            sentencia.setString(2, entity.getContrasena());
            sentencia.setObject(3, entity.getCodigoRol());
            sentencia.setObject(4, uuid);

            var filasActualizadas = sentencia.executeUpdate();
            if (filasActualizadas == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el usuario para actualizar.",
                        "No existe un registro en la tabla 'usuario' con ID=[" + uuid + "]"
                );
            }
        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible actualizar los datos del usuario.",
                    "SQLException en update de UsuarioPostgreSQLDAO. SQL=[" + sql + "]",
                    excepcion
            );
        }
    }

    @Override
    public void delete(final UUID id) throws DataFondaControlException {
        if (UtilObjeto.esNulo(id)) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        final var sql = new StringBuilder("DELETE FROM usuario WHERE id = ?");

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, id);

            var filasEliminadas = sentencia.executeUpdate();
            if (filasEliminadas == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el usuario a eliminar.",
                        "No existe un registro con el ID=[" + id + "]"
                );
            }
        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible eliminar el usuario.",
                    "SQLException en delete de UsuarioPostgreSQLDAO. SQL=[" + sql + "]",
                    excepcion
            );
        }
    }

    @Override
    public UsuarioEntity findById(final UUID id) throws DataFondaControlException {
        if (UtilObjeto.esNulo(id)) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        final var sql = new StringBuilder(
                "SELECT id, nombre, contrasena, codigorol FROM usuario WHERE id = ?"
        );

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, id);
            try (var resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return new UsuarioEntity(
                            (UUID) resultado.getObject("id"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigorol"),
                            resultado.getString("contrasena")
                    );
                }
            }
        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible obtener el usuario con el ID proporcionado.",
                    "SQLException en findById de UsuarioPostgreSQLDAO. SQL=[" + sql + "]",
                    excepcion
            );
        }

        return null;
    }

    @Override
    public List<UsuarioEntity> listByCodigo(final UUID id) throws DataFondaControlException {
        if (UtilObjeto.esNulo(id)) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        final var sql = new StringBuilder(
                "SELECT id, nombre, contrasena, codigorol FROM usuario WHERE id = ?"
        );
        final var resultados = new ArrayList<UsuarioEntity>();

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, id);
            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new UsuarioEntity(
                            (UUID) resultado.getObject("id"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigorol"),
                            resultado.getString("contrasena")
                    ));
                }
            }
        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible consultar usuarios por ID.",
                    "SQLException en listByCodigo de UsuarioPostgreSQLDAO. SQL=[" + sql + "]",
                    excepcion
            );
        }

        return resultados;
    }

    @Override
    public List<UsuarioEntity> listByFilter(final UsuarioEntity filtro) throws DataFondaControlException {
        if (UtilObjeto.esNulo(filtro)) {
            throw new IllegalArgumentException("El filtro no puede ser nulo.");
        }
        if (UtilTexto.getInstancia().esNula(filtro.getNombre())) {
            throw new IllegalArgumentException("El nombre en el filtro no puede ser nulo ni vacío.");
        }

        final var sql = new StringBuilder(
                "SELECT id, nombre, contrasena, codigorol FROM usuario WHERE nombre = ?"
        );
        final var resultados = new ArrayList<UsuarioEntity>();

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setString(1, filtro.getNombre().trim());

            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new UsuarioEntity(
                            (UUID) resultado.getObject("id"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigorol"),
                            resultado.getString("contrasena")
                    ));
                }
            }

        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible consultar usuarios por filtro.",
                    "SQLException en listByFilter de UsuarioPostgreSQLDAO. SQL=[" + sql + "]",
                    excepcion
            );
        }

        return resultados;
    }

    @Override
    public List<UsuarioEntity> listAll() throws DataFondaControlException {
        final var sql = new StringBuilder(
                "SELECT id, nombre, contrasena, codigorol FROM usuario"
        );
        final var resultados = new ArrayList<UsuarioEntity>();

        try (var sentencia = conexion.prepareStatement(sql.toString());
             var resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                resultados.add(new UsuarioEntity(
                        (UUID) resultado.getObject("id"),
                        resultado.getString("nombre"),
                        (UUID) resultado.getObject("codigorol"),
                        resultado.getString("contrasena")
                ));
            }

        } catch (final SQLException excepcion) {
            throw DataFondaControlException.reportar(
                    "No fue posible consultar todos los usuarios.",
                    "SQLException en listAll de UsuarioPostgreSQLDAO. SQL=[" + sql + "]",
                    excepcion
            );
        }

        return resultados;
    }

    /**
     * Validación común antes de insert y update: nombre, contraseña y rol no nulos ni vacíos.
     */
    private void validarEntidadParaInsertYUpdate(final UsuarioEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad de usuario no puede ser nula.");
        }
        if (UtilTexto.getInstancia().esNula(entity.getNombre())) {
            throw new IllegalArgumentException("El nombre del usuario no puede ser nulo ni vacío.");
        }
        if (UtilTexto.getInstancia().esNula(entity.getContrasena())) {
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía.");
        }
        if (UtilObjeto.esNulo(entity.getCodigoRol())) {
            throw new IllegalArgumentException("El código de rol no puede ser nulo.");
        }
    }
}
