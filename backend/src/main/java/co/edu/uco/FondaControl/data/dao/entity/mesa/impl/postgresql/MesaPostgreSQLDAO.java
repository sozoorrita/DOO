package co.edu.uco.FondaControl.data.dao.entity.mesa.impl.postgresql;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.entity.mesa.MesaDAO;
import co.edu.uco.FondaControl.entity.MesaEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MesaPostgreSQLDAO implements MesaDAO {

    private final Connection conexion;

    public MesaPostgreSQLDAO(final Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void create(MesaEntity entity) throws DataFondaControlException {
        final String sql = "INSERT INTO mesa(codigo, nombre, codigoestadomesa) VALUES (?, ?, ?)";
        try (final PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, entity.getCodigo());
            sentencia.setString(2, entity.getNombre());
            sentencia.setObject(3, entity.getCodigoEstadoMesa());
            sentencia.executeUpdate();
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al registrar la mesa en la base de datos.",
                    "Ocurrió un error al intentar ejecutar el SQL=[" + sql + "] con los valores: codigo=" + entity.getCodigo() +
                            ", nombre=" + entity.getNombre() + ", codigoEstadoMesa=" + entity.getCodigoEstadoMesa() + ". Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }
    }

    @Override
    public MesaEntity findById(UUID codigo) throws DataFondaControlException {
        final String sql = "SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE codigo = ?";
        try (final PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, codigo);
            try (final ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    );
                }
            }
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al consultar la mesa por código.",
                    "Se presentó un error al ejecutar SQL=[" + sql + "] con código=[" + codigo + "]. Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }
        return null;
    }

    @Override
    public List<MesaEntity> listAll() throws DataFondaControlException {
        final String sql = "SELECT codigo, nombre, codigoestadomesa FROM mesa";
        final List<MesaEntity> resultados = new ArrayList<>();

        try (final PreparedStatement sentencia = conexion.prepareStatement(sql);
             final ResultSet resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                resultados.add(new MesaEntity(
                        (UUID) resultado.getObject("codigo"),
                        resultado.getString("nombre"),
                        (UUID) resultado.getObject("codigoestadomesa")
                ));
            }
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al listar todas las mesas.",
                    "Se produjo una excepción ejecutando SQL=[" + sql + "]. Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }

        return resultados;
    }

    @Override
    public List<MesaEntity> listByCodigo(UUID codigo) throws DataFondaControlException {
        final List<MesaEntity> resultados = new ArrayList<>();
        final String sql = "SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE codigo = ?";

        try (final PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, codigo);
            try (final ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    ));
                }
            }
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al listar mesa por código.",
                    "Error ejecutando SQL=[" + sql + "] con código=[" + codigo + "]. Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }

        return resultados;
    }

    @Override
    public List<MesaEntity> listByEstado(UUID codigoEstadoMesa) throws DataFondaControlException {
        final List<MesaEntity> resultados = new ArrayList<>();
        final String sql = "SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE codigoestadomesa = ?";

        try (final PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, codigoEstadoMesa);
            try (final ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    ));
                }
            }
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al listar mesas por estado.",
                    "Se produjo un error ejecutando SQL=[" + sql + "] con codigoEstadoMesa=[" + codigoEstadoMesa + "]. Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }

        return resultados;
    }

    @Override
    public List<MesaEntity> listByFilter(MesaEntity entity) throws DataFondaControlException {
        final List<MesaEntity> resultados = new ArrayList<>();
        final StringBuilder sql = new StringBuilder("SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE 1=1 ");
        final List<Object> parametros = new ArrayList<>();

        if (!UtilObjeto.esNulo(entity)) {
            if (!UtilObjeto.esNulo(entity.getCodigo())) {
                sql.append("AND codigo = ? ");
                parametros.add(entity.getCodigo());
            }
            if (!UtilTexto.getInstancia().esNula(entity.getNombre())) {
                sql.append("AND nombre ILIKE ? ");
                parametros.add("%" + entity.getNombre() + "%");
            }
            if (!UtilObjeto.esNulo(entity.getCodigoEstadoMesa())) {
                sql.append("AND codigoestadomesa = ? ");
                parametros.add(entity.getCodigoEstadoMesa());
            }
        }

        try (final PreparedStatement sentencia = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                sentencia.setObject(i + 1, parametros.get(i));
            }

            try (final ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    ));
                }
            }
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al filtrar mesas.",
                    "Se produjo una excepción ejecutando SQL=[" + sql + "] con parámetros=[" + parametros + "]. Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }

        return resultados;
    }

    @Override
    public void update(UUID codigo, MesaEntity entity) throws DataFondaControlException {
        final String sql = "UPDATE mesa SET nombre = ?, codigoestadomesa = ? WHERE codigo = ?";
        try (final PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setString(1, entity.getNombre());
            sentencia.setObject(2, entity.getCodigoEstadoMesa());
            sentencia.setObject(3, codigo);
            sentencia.executeUpdate();
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al actualizar la mesa.",
                    "Fallo al ejecutar SQL=[" + sql + "] con valores: nombre=" + entity.getNombre() +
                            ", codigoEstadoMesa=" + entity.getCodigoEstadoMesa() + ", codigo=" + codigo + ". Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }
    }

    @Override
    public void delete(UUID codigo) throws DataFondaControlException {
        final String sql = "DELETE FROM mesa WHERE codigo = ?";
        try (final PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setObject(1, codigo);
            sentencia.executeUpdate();
        } catch (final Exception excepcion) {
            throw DataFondaControlException.reportar(
                    "Error al eliminar la mesa.",
                    "Error ejecutando SQL=[" + sql + "] con código=[" + codigo + "]. Detalles: " + excepcion.getMessage(),
                    excepcion
            );
        }
    }
}
