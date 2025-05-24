package co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.entity.UsuarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SesionTrabajoPostgreSQLDAO implements SesionTrabajoDAO {

    private final Connection connection;

    public SesionTrabajoPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(SesionTrabajoEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        final String sql = "INSERT INTO sesiontrabajo (idusuario, fechaapertura, fechacierre, basecaja) VALUES (?, ?, ?, ?) RETURNING codigo";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getIdUsuario().getCodigo());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getFechaApertura()));
            ps.setTimestamp(3, entity.getFechaCierre() != null ? java.sql.Timestamp.valueOf(entity.getFechaCierre()) : null);
            ps.setBigDecimal(4, entity.getBaseCaja());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entity.setCodigo((UUID) rs.getObject("codigo"));
                } else {
                    throw DataFondaControlException.reportar(
                            "No se pudo obtener el UUID generado automáticamente para la sesión de trabajo.",
                            "La sentencia INSERT en create() no retornó ningún UUID."
                    );
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al crear la sesión de trabajo.",
                    "SQLException en create(): " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void update(UUID codigo, SesionTrabajoEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }
        validarEntidad(entity);

        final String sql = "UPDATE sesiontrabajo SET idusuario = ?, fechaapertura = ?, fechacierre = ?, basecaja = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getIdUsuario().getCodigo());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getFechaApertura()));
            ps.setTimestamp(3, entity.getFechaCierre() != null ? java.sql.Timestamp.valueOf(entity.getFechaCierre()) : null);
            ps.setBigDecimal(4, entity.getBaseCaja());
            ps.setObject(5, codigo);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró la sesión de trabajo para actualizar.",
                        "No existe sesión de trabajo con código: " + codigo
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar la sesión de trabajo.",
                    "SQLException en update(): " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public SesionTrabajoEntity findById(UUID codigo) throws DataFondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        final String sql = "SELECT codigo, idusuario, fechaapertura, fechacierre, basecaja FROM sesiontrabajo WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToEntity(rs);
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al buscar la sesión de trabajo por código.",
                    "SQLException en findById(): " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    @Override
    public SesionTrabajoEntity findByUsuario(UUID idUsuario) throws DataFondaControlException {
        if (UtilObjeto.esNulo(idUsuario)) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo.");
        }

        final String sql = "SELECT codigo, idusuario, fechaapertura, fechacierre, basecaja FROM sesiontrabajo WHERE idusuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToEntity(rs);
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al buscar la sesión de trabajo por usuario.",
                    "SQLException en findByUsuario(): " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    private SesionTrabajoEntity mapToEntity(ResultSet rs) throws SQLException {
        return new SesionTrabajoEntity(
                (UUID) rs.getObject("codigo"),
                UsuarioEntity.obtenerDesdeUUID((UUID) rs.getObject("idusuario")),
                rs.getBigDecimal("basecaja"),
                rs.getTimestamp("fechaapertura").toLocalDateTime(),
                rs.getTimestamp("fechacierre") != null ? rs.getTimestamp("fechacierre").toLocalDateTime() : null
        );
    }

    private void validarEntidad(SesionTrabajoEntity entity) {
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
