package co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class InformeCajaPostgreSQLDAO implements InformeCajaDAO {
    private final Connection connection;

    public InformeCajaPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void update(UUID uuid, InformeCajaEntity entity) {
        if (UtilObjeto.esNulo(uuid) || UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("El UUID y la entidad no pueden ser nulos.");
        }

        String sql = "UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, entity.getCodigoSesionTrabajo());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entity.getFecha()));
            preparedStatement.setBigDecimal(3, entity.getTotalVenta());
            preparedStatement.setBigDecimal(4, entity.getPagoEfectivo());
            preparedStatement.setBigDecimal(5, entity.getPagoTransferencia());
            preparedStatement.setObject(6, uuid);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No se encontró el registro con el código especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el informe de caja.", e);
        }
    }

    @Override
    public void update(InformeCajaDTO informeCaja) {
        if (UtilObjeto.esNulo(informeCaja) || UtilObjeto.esNulo(informeCaja.getCodigo())) {
            throw new IllegalArgumentException("El informe de caja y su código no pueden ser nulos.");
        }

        String sql = "UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, informeCaja.getCodigoSesionTrabajo());
            preparedStatement.setDate(2, java.sql.Date.valueOf(informeCaja.getFecha()));
            preparedStatement.setBigDecimal(3, informeCaja.getTotalVenta());
            preparedStatement.setBigDecimal(4, informeCaja.getPagoEfectivo());
            preparedStatement.setBigDecimal(5, informeCaja.getPagoTransferencia());
            preparedStatement.setObject(6, informeCaja.getCodigo());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No se encontró el registro con el código especificado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el informe de caja.", e);
        }
    }

    @Override
    public void update(InformeCajaEntity informeCajaEntity, UUID entity) {

    }
}