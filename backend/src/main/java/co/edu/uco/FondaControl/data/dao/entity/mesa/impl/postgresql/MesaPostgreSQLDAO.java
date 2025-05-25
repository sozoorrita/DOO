package co.edu.uco.FondaControl.data.dao.entity.mesa.impl.postgresql;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.data.dao.entity.mesa.MesaDAO;
import co.edu.uco.FondaControl.entity.MesaEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        var sentenciaSQL = new StringBuilder("INSERT INTO mesa(codigo, nombre, codigoestadomesa) VALUES (?, ?, ?)");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            sentencia.setObject(1, entity.getCodigo());
            sentencia.setString(2, entity.getNombre());
            sentencia.setObject(3, entity.getCodigoEstadoMesa());
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de registrar la mesa.";
            var mensajeTecnico = "SQLException en 'create' con SQL=[" + sentenciaSQL + "], codigo=[" + entity.getCodigo() + "], nombre=[" + entity.getNombre() + "], codigoEstadoMesa=[" + entity.getCodigoEstadoMesa() + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public MesaEntity findById(UUID codigo) throws DataFondaControlException {
        var sentenciaSQL = new StringBuilder("SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE codigo = ?");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            sentencia.setObject(1, codigo);
            try (var resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    );
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de consultar la mesa por ID.";
            var mensajeTecnico = "SQLException en 'findById' con SQL=[" + sentenciaSQL + "], ID=[" + codigo + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
        return null;
    }

    @Override
    public List<MesaEntity> listAll() throws DataFondaControlException {
        var resultados = new ArrayList<MesaEntity>();
        var sentenciaSQL = new StringBuilder("SELECT codigo, nombre, codigoestadomesa FROM mesa");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString());
             var resultado = sentencia.executeQuery()) {
            while (resultado.next()) {
                resultados.add(new MesaEntity(
                        (UUID) resultado.getObject("codigo"),
                        resultado.getString("nombre"),
                        (UUID) resultado.getObject("codigoestadomesa")
                ));
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de listar todas las mesas.";
            var mensajeTecnico = "SQLException en 'listAll' con SQL=[" + sentenciaSQL + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public List<MesaEntity> listByCodigo(UUID codigo) throws DataFondaControlException {
        var resultados = new ArrayList<MesaEntity>();
        var sentenciaSQL = new StringBuilder("SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE codigo = ?");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            sentencia.setObject(1, codigo);
            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    ));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de consultar las mesas por código.";
            var mensajeTecnico = "SQLException en 'listByCodigo' con SQL=[" + sentenciaSQL + "], código=[" + codigo + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public List<MesaEntity> listByEstado(UUID codigoEstadoMesa) throws DataFondaControlException {
        var resultados = new ArrayList<MesaEntity>();
        var sentenciaSQL = new StringBuilder("SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE codigoestadomesa = ?");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            sentencia.setObject(1, codigoEstadoMesa);
            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    ));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de consultar mesas por estado.";
            var mensajeTecnico = "SQLException en 'listByEstado' con SQL=[" + sentenciaSQL + "], codigoEstadoMesa=[" + codigoEstadoMesa + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public List<MesaEntity> listByFilter(MesaEntity entity) throws DataFondaControlException {
        var resultados = new ArrayList<MesaEntity>();
        var sentenciaSQL = new StringBuilder("SELECT codigo, nombre, codigoestadomesa FROM mesa WHERE 1=1 ");
        var parametros = new ArrayList<>();

        if (!UtilObjeto.esNulo(entity)) {
            if (!UtilObjeto.esNulo(entity.getCodigo())) {
                sentenciaSQL.append("AND codigo = ? ");
                parametros.add(entity.getCodigo());
            }
            if (!UtilTexto.getInstancia().esNula(entity.getNombre())) {
                sentenciaSQL.append("AND nombre ILIKE ? ");
                parametros.add("%" + entity.getNombre() + "%");
            }
            if (!UtilObjeto.esNulo(entity.getCodigoEstadoMesa())) {
                sentenciaSQL.append("AND codigoestadomesa = ? ");
                parametros.add(entity.getCodigoEstadoMesa());
            }
        }

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                sentencia.setObject(i + 1, parametros.get(i));
            }

            try (var resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(new MesaEntity(
                            (UUID) resultado.getObject("codigo"),
                            resultado.getString("nombre"),
                            (UUID) resultado.getObject("codigoestadomesa")
                    ));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de consultar las mesas por filtro.";
            var mensajeTecnico = "SQLException en 'listByFilter' con SQL=[" + sentenciaSQL + "], parámetros=[" + parametros + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public void update(UUID codigo, MesaEntity entity) throws DataFondaControlException {
        var sentenciaSQL = new StringBuilder("UPDATE mesa SET nombre = ?, codigoestadomesa = ? WHERE codigo = ?");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            sentencia.setString(1, entity.getNombre());
            sentencia.setObject(2, entity.getCodigoEstadoMesa());
            sentencia.setObject(3, codigo);
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de actualizar la mesa.";
            var mensajeTecnico = "SQLException en 'update' con SQL=[" + sentenciaSQL + "], código=[" + codigo + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public void delete(UUID codigo) throws DataFondaControlException {
        var sentenciaSQL = new StringBuilder("DELETE FROM mesa WHERE codigo = ?");

        try (var sentencia = conexion.prepareStatement(sentenciaSQL.toString())) {
            sentencia.setObject(1, codigo);
            sentencia.executeUpdate();
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se ha presentado un problema tratando de eliminar la mesa.";
            var mensajeTecnico = "SQLException en 'delete' con SQL=[" + sentenciaSQL + "], código=[" + codigo + "]";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }
}
