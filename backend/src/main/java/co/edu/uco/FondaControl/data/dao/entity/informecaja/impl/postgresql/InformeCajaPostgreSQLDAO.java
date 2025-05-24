package co.edu.uco.FondaControl.data.dao.entity.informecaja.impl.postgresql;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.entity.informecaja.InformeCajaDAO;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InformeCajaPostgreSQLDAO implements InformeCajaDAO {

    private final Connection connection;

    public InformeCajaPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(InformeCajaEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        final String sql = """
            INSERT INTO informe_caja (codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia)
            VALUES (?, ?, ?, ?, ?) RETURNING codigo
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getCodigoSesionTrabajo());
            ps.setDate(2, Date.valueOf(entity.getFecha()));
            ps.setBigDecimal(3, entity.getTotalVenta());
            ps.setBigDecimal(4, entity.getPagoEfectivo());
            ps.setBigDecimal(5, entity.getPagoTransferencia());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entity.setCodigo((UUID) rs.getObject("codigo"));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible registrar el informe de caja.",
                    "Error SQL al insertar informe_caja: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<InformeCajaEntity> listByFilter(InformeCajaEntity filtro) throws DataFondaControlException {
        final String sql = "SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja WHERE codigosesiontrabajo = ?";
        List<InformeCajaEntity> resultados = new ArrayList<>();

        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigoSesionTrabajo())) {
            throw new IllegalArgumentException("El filtro y su código de sesión de trabajo no pueden ser nulos.");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, filtro.getCodigoSesionTrabajo());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al filtrar los informes de caja.",
                    "SQLException en listByFilter(): " + e.getMessage(),
                    e
            );
        }

        return resultados;
    }

    @Override
    public List<InformeCajaEntity> listAll() throws DataFondaControlException {
        final String sql = "SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja";
        List<InformeCajaEntity> resultados = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultados.add(mapResultSetToEntity(rs));
            }

        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al listar todos los informes de caja.",
                    "SQLException en listAll(): " + e.getMessage(),
                    e
            );
        }

        return resultados;
    }

    @Override
    public List<InformeCajaEntity> listByCodigo(UUID uuid) throws DataFondaControlException {
        final String sql = "SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja WHERE codigo = ?";
        List<InformeCajaEntity> resultados = new ArrayList<>();

        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al consultar informe de caja por código.",
                    "SQLException en listByCodigo(): " + e.getMessage(),
                    e
            );
        }

        return resultados;
    }

    @Override
    public InformeCajaEntity findById(UUID codigo) throws DataFondaControlException {
        final String sql = "SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja WHERE codigo = ?";

        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible consultar el informe de caja por ID.",
                    "SQLException en findById(): " + e.getMessage(),
                    e
            );
        }

        return null;
    }

    @Override
    public void update(InformeCajaDTO dto) throws DataFondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())) {
            throw new IllegalArgumentException("El DTO y su código no pueden ser nulos.");
        }

        final String sql = "UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, dto.getCodigoSesionTrabajo());
            ps.setDate(2, Date.valueOf(dto.getFecha()));
            ps.setBigDecimal(3, dto.getTotalVenta());
            ps.setBigDecimal(4, dto.getPagoEfectivo());
            ps.setBigDecimal(5, dto.getPagoTransferencia());
            ps.setObject(6, dto.getCodigo());

            if (ps.executeUpdate() == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el informe de caja a actualizar.",
                        "Código no encontrado: " + dto.getCodigo()
                );
            }

        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar informe de caja desde DTO.",
                    "SQLException en update(InformeCajaDTO): " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void update(UUID uuid, InformeCajaEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        final String sql = "UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getCodigoSesionTrabajo());
            ps.setDate(2, Date.valueOf(entity.getFecha()));
            ps.setBigDecimal(3, entity.getTotalVenta());
            ps.setBigDecimal(4, entity.getPagoEfectivo());
            ps.setBigDecimal(5, entity.getPagoTransferencia());
            ps.setObject(6, uuid);

            if (ps.executeUpdate() == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el informe de caja a actualizar.",
                        "No se afectó ninguna fila con el código: " + uuid
                );
            }

        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                    "No fue posible actualizar el informe de caja.",
                    "SQLException en update(): " + e.getMessage(),
                    e
            );
        }
    }

    private InformeCajaEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new InformeCajaEntity(
                (UUID) rs.getObject("codigo"),
                (UUID) rs.getObject("codigosesiontrabajo"),
                rs.getDate("fecha").toLocalDate(),
                rs.getBigDecimal("totalventa"),
                rs.getBigDecimal("pagoefectivo"),
                rs.getBigDecimal("pagotransferencia")
        );
    }

    private void validarEntidad(InformeCajaEntity entity) {
        if (UtilObjeto.esNulo(entity)) throw new IllegalArgumentException("La entidad no puede ser nula.");
        if (UtilObjeto.esNulo(entity.getCodigoSesionTrabajo())) throw new IllegalArgumentException("El código de sesión de trabajo es obligatorio.");
        if (UtilObjeto.esNulo(entity.getFecha())) throw new IllegalArgumentException("La fecha es obligatoria.");
        if (UtilObjeto.esNulo(entity.getTotalVenta())) throw new IllegalArgumentException("El total de venta es obligatorio.");
        if (UtilObjeto.esNulo(entity.getPagoEfectivo())) throw new IllegalArgumentException("El valor de pago en efectivo es obligatorio.");
        if (UtilObjeto.esNulo(entity.getPagoTransferencia())) throw new IllegalArgumentException("El valor de pago por transferencia es obligatorio.");
    }
}
