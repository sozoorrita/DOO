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

public final class InformeCajaPostgreSQLDAO implements InformeCajaDAO {

    private final Connection conexion;

    public InformeCajaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final InformeCajaEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        final var sql = new StringBuilder("""
            INSERT INTO informe_caja (codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia)
            VALUES (?, ?, ?, ?, ?) RETURNING codigo
        """);

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, entity.getCodigoSesionTrabajo());
            sentencia.setDate(2, Date.valueOf(entity.getFecha()));
            sentencia.setBigDecimal(3, entity.getTotalVenta());
            sentencia.setBigDecimal(4, entity.getPagoEfectivo());
            sentencia.setBigDecimal(5, entity.getPagoTransferencia());

            try (var resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    entity.setCodigo((UUID) resultado.getObject("codigo"));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de registrar el informe de caja.";
            var mensajeTecnico = "SQLException en 'create' con SQL=[" + sql + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public List<InformeCajaEntity> listByFilter(final InformeCajaEntity filtro) throws DataFondaControlException {
        final var resultados = new ArrayList<InformeCajaEntity>();
        final var sql = new StringBuilder("SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja WHERE codigosesiontrabajo = ?");

        if (UtilObjeto.esNulo(filtro) || UtilObjeto.esNulo(filtro.getCodigoSesionTrabajo())) {
            throw new IllegalArgumentException("El filtro y su código de sesión de trabajo no pueden ser nulos.");
        }

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, filtro.getCodigoSesionTrabajo());
            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(mapResultSetToEntity(resultado));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de filtrar los informes de caja.";
            var mensajeTecnico = "SQLException en 'listByFilter' con SQL=[" + sql + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public List<InformeCajaEntity> listAll() throws DataFondaControlException {
        final var resultados = new ArrayList<InformeCajaEntity>();
        final var sql = new StringBuilder("SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja");

        try (var sentencia = conexion.prepareStatement(sql.toString());
             var resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                resultados.add(mapResultSetToEntity(resultado));
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de listar todos los informes de caja.";
            var mensajeTecnico = "SQLException en 'listAll' con SQL=[" + sql + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public List<InformeCajaEntity> listByCodigo(final UUID uuid) throws DataFondaControlException {
        final var resultados = new ArrayList<InformeCajaEntity>();
        final var sql = new StringBuilder("SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja WHERE codigo = ?");

        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, uuid);
            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(mapResultSetToEntity(resultado));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de consultar el informe de caja por código.";
            var mensajeTecnico = "SQLException en 'listByCodigo' con SQL=[" + sql + "], código=[" + uuid + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public InformeCajaEntity findById(final UUID codigo) throws DataFondaControlException {
        final var sql = new StringBuilder("SELECT codigo, codigosesiontrabajo, fecha, totalventa, pagoefectivo, pagotransferencia FROM informe_caja WHERE codigo = ?");

        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, codigo);
            try (var resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapResultSetToEntity(resultado);
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de consultar el informe de caja por ID.";
            var mensajeTecnico = "SQLException en 'findById' con SQL=[" + sql + "], ID=[" + codigo + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return null;
    }

    @Override
    public void update(final InformeCajaDTO dto) throws DataFondaControlException {
        if (UtilObjeto.esNulo(dto) || UtilObjeto.esNulo(dto.getCodigo())) {
            throw new IllegalArgumentException("El DTO y su código no pueden ser nulos.");
        }

        final var sql = new StringBuilder("UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?");

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, dto.getCodigoSesionTrabajo());
            sentencia.setDate(2, Date.valueOf(dto.getFecha()));
            sentencia.setBigDecimal(3, dto.getTotalVenta());
            sentencia.setBigDecimal(4, dto.getPagoEfectivo());
            sentencia.setBigDecimal(5, dto.getPagoTransferencia());
            sentencia.setObject(6, dto.getCodigo());

            if (sentencia.executeUpdate() == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el informe de caja a actualizar.",
                        "Código no encontrado: " + dto.getCodigo()
                );
            }

        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de actualizar el informe de caja.";
            var mensajeTecnico = "SQLException en 'update(dto)' con SQL=[" + sql + "], código=[" + dto.getCodigo() + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public void update(final UUID uuid, final InformeCajaEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        final var sql = new StringBuilder("UPDATE informe_caja SET codigosesiontrabajo = ?, fecha = ?, totalventa = ?, pagoefectivo = ?, pagotransferencia = ? WHERE codigo = ?");

        try (var sentencia = conexion.prepareStatement(sql.toString())) {
            sentencia.setObject(1, entity.getCodigoSesionTrabajo());
            sentencia.setDate(2, Date.valueOf(entity.getFecha()));
            sentencia.setBigDecimal(3, entity.getTotalVenta());
            sentencia.setBigDecimal(4, entity.getPagoEfectivo());
            sentencia.setBigDecimal(5, entity.getPagoTransferencia());
            sentencia.setObject(6, uuid);

            if (sentencia.executeUpdate() == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el informe de caja a actualizar.",
                        "No se afectó ninguna fila con el código: " + uuid
                );
            }

        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de actualizar el informe de caja.";
            var mensajeTecnico = "SQLException en 'update(entity)' con SQL=[" + sql + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    private InformeCajaEntity mapResultSetToEntity(final ResultSet rs) throws SQLException {
        return new InformeCajaEntity(
                (UUID) rs.getObject("codigo"),
                (UUID) rs.getObject("codigosesiontrabajo"),
                rs.getDate("fecha").toLocalDate(),
                rs.getBigDecimal("totalventa"),
                rs.getBigDecimal("pagoefectivo"),
                rs.getBigDecimal("pagotransferencia")
        );
    }

    private void validarEntidad(final InformeCajaEntity entity) {
        if (UtilObjeto.esNulo(entity)) throw new IllegalArgumentException("La entidad no puede ser nula.");
        if (UtilObjeto.esNulo(entity.getCodigoSesionTrabajo())) throw new IllegalArgumentException("El código de sesión de trabajo es obligatorio.");
        if (UtilObjeto.esNulo(entity.getFecha())) throw new IllegalArgumentException("La fecha es obligatoria.");
        if (UtilObjeto.esNulo(entity.getTotalVenta())) throw new IllegalArgumentException("El total de venta es obligatorio.");
        if (UtilObjeto.esNulo(entity.getPagoEfectivo())) throw new IllegalArgumentException("El valor de pago en efectivo es obligatorio.");
        if (UtilObjeto.esNulo(entity.getPagoTransferencia())) throw new IllegalArgumentException("El valor de pago por transferencia es obligatorio.");
    }
}
