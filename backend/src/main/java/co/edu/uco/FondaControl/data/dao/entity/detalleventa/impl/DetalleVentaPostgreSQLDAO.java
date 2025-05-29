package co.edu.uco.FondaControl.data.dao.entity.detalleventa.impl;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.detalleventa.DetalleVentaDAO;
import co.edu.uco.FondaControl.entity.DetalleVentaEntity;
import co.edu.uco.FondaControl.entity.ProductoEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetalleVentaPostgreSQLDAO implements DetalleVentaDAO {
    private final Connection conexion;

    private static final String SQL_INSERT =
        "INSERT INTO detalleventa(codigo, producto, codigo_venta, precioaplicado, cantidad) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE detalleventa SET producto = ?, codigo_venta = ?, precioaplicado = ?, cantidad = ? WHERE codigo = ?";
    private static final String SQL_DELETE =
        "DELETE FROM detalleventa WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM detalleventa WHERE codigo = ?";
    private static final String SQL_LIST_ALL =
        "SELECT * FROM detalleventa";

    public DetalleVentaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final DetalleVentaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setObject(2, entity.getProducto() != null ? entity.getProducto().getCodigo() : null);
            ps.setObject(3, entity.getCodigoVenta());
            ps.setDouble(4, entity.getPrecioAplicado());
            ps.setInt(5, entity.getCantidad());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear detalle de venta",
                "create@detalleventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(final UUID id, final DetalleVentaEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setObject(1, entity.getProducto() != null ? entity.getProducto().getCodigo() : null);
            ps.setObject(2, entity.getCodigoVenta());
            ps.setDouble(3, entity.getPrecioAplicado());
            ps.setInt(4, entity.getCantidad());
            ps.setObject(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar detalle de venta",
                "update@detalleventa - " + e.getMessage(), e
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
                "Error al eliminar detalle de venta",
                "delete@detalleventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<DetalleVentaEntity> listByFilter(final DetalleVentaEntity filter) throws DataFondaControlException {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        List<Object> params = new ArrayList<>();
        boolean hasWhere = false;

        if (filter.getProducto() != null && filter.getProducto().getCodigo() != null) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" producto = ?");
            params.add(filter.getProducto().getCodigo());
            hasWhere = true;
        }

        if (filter.getCodigoVenta() != null) {
            sql.append(hasWhere ? " AND" : " WHERE");
            sql.append(" codigo_venta = ?");
            params.add(filter.getCodigoVenta());
            hasWhere = true;
        }

        List<DetalleVentaEntity> results = new ArrayList<>();
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
                "Error al filtrar detalle de venta",
                "listByFilter@detalleventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<DetalleVentaEntity> listAll() throws DataFondaControlException {
        List<DetalleVentaEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar detalles de venta",
                "listAll@detalleventa - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<DetalleVentaEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
        DetalleVentaEntity entity = findById(codigo);
        return entity == null ? List.of() : List.of(entity);
    }

    public DetalleVentaEntity findById(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al buscar detalle de venta por ID",
                "findById@detalleventa - " + e.getMessage(), e
            );
        }
    }

    private DetalleVentaEntity mapRow(final ResultSet rs) throws SQLException {
        UUID productoId = rs.getObject("producto", UUID.class);
        ProductoEntity producto = new ProductoEntity();
        producto.setCodigo(productoId);

        return DetalleVentaEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .producto(producto)
            .codigoVenta(rs.getObject("codigo_venta", UUID.class))
            .precioAplicado(rs.getDouble("precioaplicado"))
            .cantidad(rs.getInt("cantidad"))
            .crear();
    }
}
