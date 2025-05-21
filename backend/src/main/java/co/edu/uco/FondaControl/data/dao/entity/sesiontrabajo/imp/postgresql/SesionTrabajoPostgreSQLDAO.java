package co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.sesiontrabajo.SesionTrabajoDAO;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;
import co.edu.uco.FondaControl.entity.UsuarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.math.BigDecimal;
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
    public void create(SesionTrabajoEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }

        String sql = "INSERT INTO sesiontrabajo (codigo, idusuario, fechaapertura, fechacierre, basecaja) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, entity.getCodigo());
            preparedStatement.setObject(2, entity.getIdUsuario().getCodigo());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getFechaApertura()));
            preparedStatement.setTimestamp(4, entity.getFechaCierre() != null ? java.sql.Timestamp.valueOf(entity.getFechaCierre()) : null);
            preparedStatement.setBigDecimal(5, entity.getBaseCaja());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la sesión de trabajo.", e);
        }
    }

    @Override
    public void update(UUID codigo, SesionTrabajoEntity entity) {
        if (UtilObjeto.esNulo(codigo) || UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("El código y la entidad no pueden ser nulos.");
        }

        String sql = "UPDATE sesiontrabajo SET idusuario = ?, fechaapertura = ?, fechacierre = ?, basecaja = ? WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, entity.getIdUsuario().getCodigo());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getFechaApertura()));
            preparedStatement.setTimestamp(3, entity.getFechaCierre() != null ? java.sql.Timestamp.valueOf(entity.getFechaCierre()) : null);
            preparedStatement.setBigDecimal(4, entity.getBaseCaja());
            preparedStatement.setObject(5, codigo);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No se encontró el registro con el código especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la sesión de trabajo.", e);
        }
    }

    @Override
    public SesionTrabajoEntity findById(UUID codigo) {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        String sql = "SELECT codigo, idusuario, fechaapertura, fechacierre, basecaja FROM sesiontrabajo WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, codigo);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la sesión de trabajo por ID.", e);
        }
        return null;
    }

    @Override
    public SesionTrabajoEntity findByUsuario(UUID idUsuario) {
        if (UtilObjeto.esNulo(idUsuario)) {
            throw new IllegalArgumentException("El ID de usuario no puede ser nulo.");
        }

        String sql = "SELECT codigo, idusuario, fechaapertura, fechacierre, basecaja FROM sesiontrabajo WHERE idusuario = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, idUsuario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la sesión de trabajo por usuario.", e);
        }
        return null;
    }

    private SesionTrabajoEntity mapToEntity(ResultSet resultSet) throws SQLException {
        return new SesionTrabajoEntity(
                (UUID) resultSet.getObject("codigo"),
                UsuarioEntity.obtenerDesdeUUID((UUID) resultSet.getObject("idusuario")),
                BigDecimal.valueOf(resultSet.getDouble("basecaja")),
                resultSet.getTimestamp("fechaapertura").toLocalDateTime(),
                resultSet.getTimestamp("fechacierre") != null ? resultSet.getTimestamp("fechacierre").toLocalDateTime() : null
        );
    }

    @Override
    public void update(SesionTrabajoEntity sesionTrabajoEntity, UUID entity) {

    }
}