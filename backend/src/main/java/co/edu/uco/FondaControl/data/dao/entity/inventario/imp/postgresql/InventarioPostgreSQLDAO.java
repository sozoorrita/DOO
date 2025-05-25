package co.edu.uco.FondaControl.data.dao.entity.inventario.imp.postgresql;

import co.edu.uco.FondaControl.data.dao.entity.inventario.InventarioDAO;
import co.edu.uco.FondaControl.entity.InventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventarioPostgreSQLDAO implements InventarioDAO {

    private final Connection connection;

    public InventarioPostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(InventarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        final String sql = "INSERT INTO inventario (codigo, nombreproducto, cantidad, codigoindicador) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombreProducto());
            ps.setInt(3, entity.getCantidad());
            ps.setObject(4, entity.getCodigoIndicador());

            ps.executeUpdate();
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "No fue posible registrar el inventario.",
                    "SQLException en 'create' con SQL=[" + sql + "], código=[" + entity.getCodigo() + "], nombreProducto=[" + entity.getNombreProducto() + "], cantidad=[" + entity.getCantidad() + "], codigoIndicador=[" + entity.getCodigoIndicador() + "]. Detalle: " + exception.getMessage(),
                    exception
            );
        }
    }

    @Override
    public List<InventarioEntity> listByFilter(InventarioEntity entity) throws DataFondaControlException {
        if (entity.getNombreProducto() != null && entity.getNombreProducto().length() > 50) {
            throw new IllegalArgumentException("El nombre del producto no puede exceder los 50 caracteres.");
        }

        final String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario WHERE nombreproducto LIKE ?";
        List<InventarioEntity> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + entity.getNombreProducto() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapToEntity(rs));
                }
            }
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "No fue posible filtrar los registros de inventario.",
                    "SQLException en 'listByFilter' con SQL=[" + sql + "], filtro nombreProducto=[" + entity.getNombreProducto() + "]. Detalle: " + exception.getMessage(),
                    exception
            );
        }
        return result;
    }

    @Override
    public List<InventarioEntity> listAll() throws DataFondaControlException {
        final String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario";
        List<InventarioEntity> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(mapToEntity(rs));
            }
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "No fue posible listar todos los inventarios.",
                    "SQLException en 'listAll' con SQL=[" + sql + "]. Detalle: " + exception.getMessage(),
                    exception
            );
        }
        return result;
    }

    @Override
    public List<InventarioEntity> listByCodigo(UUID uuid) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }

        final String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario WHERE codigo = ?";
        List<InventarioEntity> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, uuid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapToEntity(rs));
                }
            }
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "No fue posible obtener el inventario por código.",
                    "SQLException en 'listByCodigo' con SQL=[" + sql + "], código=[" + uuid + "]. Detalle: " + exception.getMessage(),
                    exception
            );
        }
        return result;
    }

    @Override
    public void update(UUID uuid, InventarioEntity entity) throws DataFondaControlException {
        if (UtilObjeto.esNulo(uuid)) {
            throw new IllegalArgumentException("El UUID no puede ser nulo.");
        }
        validarEntidad(entity);

        final String sql = "UPDATE inventario SET nombreproducto = ?, cantidad = ?, codigoindicador = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getNombreProducto());
            ps.setInt(2, entity.getCantidad());
            ps.setObject(3, entity.getCodigoIndicador());
            ps.setObject(4, uuid);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el inventario para actualizar.",
                        "No hay registro con el código=[" + uuid + "] para aplicar el update en 'update(...)'."
                );
            }
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar el inventario.",
                    "SQLException en 'update' con SQL=[" + sql + "], código=[" + uuid + "]. Detalle: " + exception.getMessage(),
                    exception
            );
        }
    }

    @Override
    public InventarioEntity findById(UUID codigo) throws DataFondaControlException {
        if (UtilObjeto.esNulo(codigo)) {
            throw new IllegalArgumentException("El código no puede ser nulo.");
        }

        final String sql = "SELECT codigo, nombreproducto, cantidad, codigoindicador FROM inventario WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapToEntity(rs);
                }
            }
        } catch (SQLException exception) {
            throw DataFondaControlException.reportar(
                    "No fue posible encontrar el inventario por ID.",
                    "SQLException en 'findById' con SQL=[" + sql + "], código=[" + codigo + "]. Detalle: " + exception.getMessage(),
                    exception
            );
        }
        return null;
    }

    @Override
    public void createOrUpdate(InventarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);
        if (findById(entity.getCodigo()) == null) {
            create(entity);
        } else {
            update(entity.getCodigo(), entity);
        }
    }

    private InventarioEntity mapToEntity(ResultSet rs) throws SQLException {
        return new InventarioEntity(
                (UUID) rs.getObject("codigo"),
                rs.getString("nombreproducto"),
                rs.getInt("cantidad"),
                (UUID) rs.getObject("codigoindicador")
        );
    }

    private void validarEntidad(InventarioEntity entity) {
        if (UtilObjeto.esNulo(entity)) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (UtilTexto.getInstancia().esNula(entity.getNombreProducto())) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo ni estar vacío.");
        }
        if (entity.getNombreProducto().length() > 50) {
            throw new IllegalArgumentException("El nombre del producto no puede exceder los 50 caracteres.");
        }
    }
}
