package co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql;


import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

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
    public void update(UUID uuid, InformeCajaEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        var sql = "UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getCodigoSesionTrabajo());
            ps.setDate(2, java.sql.Date.valueOf(entity.getFecha()));
            ps.setBigDecimal(3, entity.getTotalVenta());
            ps.setBigDecimal(4, entity.getPagoEfectivo());
            ps.setBigDecimal(5, entity.getPagoTransferencia());
            ps.setObject(6, uuid);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el informe de caja para actualizar.",
                        "No se encontró ningún informe_caja con código: " + uuid
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar el informe de caja.",
                    "SQLException al actualizar informe_caja: " + e.getMessage(),
                    e
            );
        } catch (Exception e) {
            throw DataFondaControlException.reportar(
                    "Error inesperado al actualizar el informe de caja.",
                    "Excepción no controlada al actualizar informe_caja: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void update(InformeCajaDTO informeCaja) throws DataFondaControlException {
        if (UtilObjeto.esNulo(informeCaja) || UtilObjeto.esNulo(informeCaja.getCodigo())) {
            throw new IllegalArgumentException("El informe de caja y su código no pueden ser nulos.");
        }

        var sql = "UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, informeCaja.getCodigoSesionTrabajo());
            ps.setDate(2, java.sql.Date.valueOf(informeCaja.getFecha()));
            ps.setBigDecimal(3, informeCaja.getTotalVenta());
            ps.setBigDecimal(4, informeCaja.getPagoEfectivo());
            ps.setBigDecimal(5, informeCaja.getPagoTransferencia());
            ps.setObject(6, informeCaja.getCodigo());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el informe de caja para actualizar.",
                        "No se encontró ningún informe_caja con código: " + informeCaja.getCodigo()
                );
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar el informe de caja.",
                    "SQLException al actualizar informe_caja desde DTO: " + e.getMessage(),
                    e
            );
        } catch (Exception e) {
            throw DataFondaControlException.reportar(
                    "Error inesperado al actualizar el informe de caja.",
                    "Excepción no controlada al actualizar informe_caja desde DTO: " + e.getMessage(),
                    e
            );
        }
    }

    private void validarEntidad(InformeCajaEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (UtilObjeto.esNulo(entity.getCodigoSesionTrabajo())) {
            throw new IllegalArgumentException("El código de sesión de trabajo no puede ser nulo.");
        }
        if (UtilObjeto.esNulo(entity.getFecha())) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        if (UtilObjeto.esNulo(entity.getTotalVenta())) {
            throw new IllegalArgumentException("El total de venta no puede ser nulo.");
        }
    }
}