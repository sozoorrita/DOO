package co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.entity.UsuarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

import java.sql.*;
import java.util.UUID;

public final class SesionTrabajoPostgreSQLDAO implements SesionTrabajoDAO {

    private final Connection conexion;

    public SesionTrabajoPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final SesionTrabajoEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        final var sql = new StringBuilder("INSERT INTO sesiontrabajo (codigo, idusuario, fechaapertura, fechacierre, basecaja) VALUES (?, ?, ?, ?, ?)");

        try (final var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, entity.getCodigo());
            sentencia.setObject(2, entity.getIdUsuario().getCodigo());
            sentencia.setTimestamp(3, Timestamp.valueOf(entity.getFechaApertura()));
            sentencia.setTimestamp(4, entity.getFechaCierre() != null ? Timestamp.valueOf(entity.getFechaCierre()) : null);
            sentencia.setBigDecimal(5, entity.getBaseCaja());

            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "No fue posible registrar la sesión de trabajo.";
            var mensajeTecnico = "SQLException en 'create' de SesionTrabajoPostgreSQLDAO. SQL=[" + sql + "], código=[" + entity.getCodigo() + "], idUsuario=[" + entity.getIdUsuario().getCodigo() + "], baseCaja=[" + entity.getBaseCaja() + "], fechaApertura=[" + entity.getFechaApertura() + "], fechaCierre=[" + entity.getFechaCierre() + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public void update(final UUID codigo, final SesionTrabajoEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }
        validarEntidad(entity);

        final var sql = new StringBuilder("UPDATE sesiontrabajo SET idusuario = ?, fechaapertura = ?, fechacierre = ?, basecaja = ? WHERE codigo = ?");

        try (final var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, entity.getIdUsuario().getCodigo());
            sentencia.setTimestamp(2, Timestamp.valueOf(entity.getFechaApertura()));
            sentencia.setTimestamp(3, entity.getFechaCierre() != null ? Timestamp.valueOf(entity.getFechaCierre()) : null);
            sentencia.setBigDecimal(4, entity.getBaseCaja());
            sentencia.setObject(5, codigo);

            final var filasActualizadas = sentencia.executeUpdate();
            if (filasActualizadas == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró la sesión de trabajo para actualizar.",
                        "No existe sesión con código=[" + codigo + "] para ejecutar 'update(...)'."
                );
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Error al actualizar la sesión de trabajo.";
            var mensajeTecnico = "SQLException en 'update' de SesionTrabajoPostgreSQLDAO. SQL=[" + sql + "], código=[" + codigo + "], idUsuario=[" + entity.getIdUsuario().getCodigo() + "], baseCaja=[" + entity.getBaseCaja() + "], fechaApertura=[" + entity.getFechaApertura() + "], fechaCierre=[" + entity.getFechaCierre() + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public SesionTrabajoEntity findById(final UUID codigo) throws DataFondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        final var sql = new StringBuilder("SELECT codigo, idusuario, fechaapertura, fechacierre, basecaja FROM sesiontrabajo WHERE codigo = ?");

        try (final var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, codigo);
            try (final var resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapToEntity(resultado);
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Error al buscar la sesión de trabajo por código.";
            var mensajeTecnico = "SQLException en 'findById' de SesionTrabajoPostgreSQLDAO. SQL=[" + sql + "], código=[" + codigo + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return null;
    }

    @Override
    public SesionTrabajoEntity findByUsuario(final UUID idUsuario) throws DataFondaControlException {
        if (UtilObjeto.esNulo(idUsuario)) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo.");
        }

        final var sql = new StringBuilder("SELECT codigo, idusuario, fechaapertura, fechacierre, basecaja FROM sesiontrabajo WHERE idusuario = ?");

        try (final var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, idUsuario);
            try (final var resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapToEntity(resultado);
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Error al buscar la sesión de trabajo por usuario.";
            var mensajeTecnico = "SQLException en 'findByUsuario' de SesionTrabajoPostgreSQLDAO. SQL=[" + sql + "], idUsuario=[" + idUsuario + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return null;
    }

    private SesionTrabajoEntity mapToEntity(final ResultSet rs) throws SQLException {
        return new SesionTrabajoEntity(
                (UUID) rs.getObject("codigo"),
                UsuarioEntity.obtenerDesdeUUID((UUID) rs.getObject("idusuario")),
                rs.getBigDecimal("basecaja"),
                rs.getTimestamp("fechaapertura").toLocalDateTime(),
                rs.getTimestamp("fechacierre") != null ? rs.getTimestamp("fechacierre").toLocalDateTime() : null
        );
    }

    private void validarEntidad(final SesionTrabajoEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (UtilObjeto.esNulo(entity.getIdUsuario()) || UtilObjeto.esNulo(entity.getIdUsuario().getCodigo())) {
            throw new IllegalArgumentException("El usuario asociado no puede ser nulo.");
        }
        if (UtilObjeto.esNulo(entity.getFechaApertura())) {
            throw new IllegalArgumentException("La fecha de apertura no puede ser nula.");
        }
        if (UtilObjeto.esNulo(entity.getBaseCaja())) {
            throw new IllegalArgumentException("La base de caja no puede ser nula.");
        }
    }
}
