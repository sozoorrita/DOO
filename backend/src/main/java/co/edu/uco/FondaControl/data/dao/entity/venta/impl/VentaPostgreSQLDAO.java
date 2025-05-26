package co.edu.uco.FondaControl.data.dao.entity.venta.impl;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.venta.VentaDAO;
import co.edu.uco.FondaControl.entity.VentaEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VentaPostgreSQLDAO implements VentaDAO {
    private final Connection conexion;

    private static final String SQL_INSERT =
        "INSERT INTO venta(codigo, fecha, totalventa, codigoformapago, codigotipoventa, codigosesiontrabajo, codigomesa) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE venta SET fecha = ?, totalventa = ?, codigoformapago = ?, codigotipoventa = ?, codigosesiontrabajo = ?, codigomesa = ? WHERE codigo = ?";
    private static final String SQL_DELETE =
        "DELETE FROM venta WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM venta WHERE codigo = ?";
    private static final String SQL_LIST_ALL =
        "SELECT * FROM venta";

    public VentaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final VentaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setObject(2, entity.getFecha());
            ps.setDouble(3, entity.getTotalVenta());
            ps.setObject(4, entity.getCodigoFormaPago());
            ps.setObject(5, entity.getCodigoTipoVenta());
            ps.setObject(6, entity.getCodigoSesionTrabajo());
            ps.setObject(7, entity.getCodigoMesa());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear venta",
                "create@venta - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(final UUID id, final VentaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setObject(1, entity.getFecha());
            ps.setDouble(2, entity.getTotalVenta());
            ps.setObject(3, entity.getCodigoFormaPago());
            ps.setObject(4, entity.getCodigoTipoVenta());
            ps.setObject(5, entity.getCodigoSesionTrabajo());
            ps.setObject(6, entity.getCodigoMesa());
            ps.setObject(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar venta",
                "update@venta - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void delete(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_DELETE)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al eliminar venta",
                "delete@venta - " + e.getMessage(), e
            );
        }
    }

    @Override
    public VentaEntity findById(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al buscar venta por ID",
                "findById@venta - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<VentaEntity> listAll() throws DataFondaControlException {
        List<VentaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar ventas",
                "listAll@venta - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<VentaEntity> listByFilter(final VentaEntity filter) throws DataFondaControlException {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        List<Object> params = new ArrayList<>();
        if (filter.getCodigoSesionTrabajo() != null) {
            sql.append(" WHERE codigosesiontrabajo = ?");
            params.add(filter.getCodigoSesionTrabajo());
        }
        List<VentaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al filtrar ventas",
                "listByFilter@venta - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<VentaEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
        VentaEntity entity = findById(codigo);
        return entity == null ? List.of() : List.of(entity);
    }

    private VentaEntity mapRow(final ResultSet rs) throws SQLException {
        return VentaEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .fecha(rs.getObject("fecha", LocalDateTime.class))
            .totalVenta(rs.getDouble("totalventa"))
            .codigoFormaPago(rs.getObject("codigoformapago", UUID.class))
            .codigoTipoVenta(rs.getObject("codigotipoventa", UUID.class))
            .codigoSesionTrabajo(rs.getObject("codigosesiontrabajo", UUID.class))
            .codigoMesa(rs.getObject("codigomesa", UUID.class))
            .crear();
    }
}
