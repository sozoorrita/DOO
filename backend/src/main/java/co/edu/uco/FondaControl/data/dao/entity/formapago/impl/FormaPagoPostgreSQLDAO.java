package co.edu.uco.FondaControl.data.dao.entity.formapago.impl;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.entity.formapago.FormaPagoDAO;
import co.edu.uco.FondaControl.entity.FormaPagoEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FormaPagoPostgreSQLDAO implements FormaPagoDAO {
    private final Connection conexion;

    private static final String SQL_INSERT =
        "INSERT INTO formapago(codigo, nombre) VALUES (?, ?)";
    private static final String SQL_UPDATE =
        "UPDATE formapago SET nombre = ? WHERE codigo = ?";
    private static final String SQL_DELETE =
        "DELETE FROM formapago WHERE codigo = ?";
    private static final String SQL_FIND_BY_ID =
        "SELECT * FROM formapago WHERE codigo = ?";
    private static final String SQL_LIST_ALL =
        "SELECT * FROM formapago";

    public FormaPagoPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(final FormaPagoEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_INSERT)) {
            ps.setObject(1, entity.getCodigo());
            ps.setString(2, entity.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al crear forma de pago",
                "create@formapago - " + e.getMessage(), e
            );
        }
    }

    @Override
    public void update(final UUID id, final FormaPagoEntity entity) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, entity.getNombre());
            ps.setObject(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al actualizar forma de pago",
                "update@formapago - " + e.getMessage(), e
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
                "Error al eliminar forma de pago",
                "delete@formapago - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<FormaPagoEntity> listByFilter(final FormaPagoEntity filter) throws DataFondaControlException {
        StringBuilder sql = new StringBuilder(SQL_LIST_ALL);
        List<Object> params = new ArrayList<>();
        if (!UtilTexto.getInstancia().esNula(filter.getNombre())) {
            sql.append(" WHERE LOWER(nombre) LIKE LOWER(?)");
            params.add("%" + filter.getNombre() + "%");
        }
        List<FormaPagoEntity> results = new ArrayList<>();
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
                "Error al filtrar formas de pago",
                "listByFilter@formapago - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<FormaPagoEntity> listAll() throws DataFondaControlException {
        List<FormaPagoEntity> results = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al listar formas de pago",
                "listAll@formapago - " + e.getMessage(), e
            );
        }
    }

    @Override
    public List<FormaPagoEntity> listByCodigo(final UUID codigo) throws DataFondaControlException {
        FormaPagoEntity entity = findById(codigo);
        return entity == null ? List.of() : List.of(entity);
    }

    @Override
    public FormaPagoEntity findById(final UUID id) throws DataFondaControlException {
        try (PreparedStatement ps = conexion.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        } catch (SQLException e) {
            throw DataFondaControlException.reportar(
                "Error al buscar forma de pago por ID",
                "findById@formapago - " + e.getMessage(), e
            );
        }
    }

    private FormaPagoEntity mapRow(final ResultSet rs) throws SQLException {
        return FormaPagoEntity.builder()
            .codigo(rs.getObject("codigo", UUID.class))
            .nombre(rs.getString("nombre"))
            .crear();
    }

	@Override
	public List<FormaPagoEntity> listByNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}
}
