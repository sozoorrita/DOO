package co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.impl.postgresql;

import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.data.dao.entity.indicadorinventario.IndicadorInventarioDAO;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class IndicadorInventarioPostgreSQLDAO implements IndicadorInventarioDAO {

    private final Connection connection;

    public IndicadorInventarioPostgreSQLDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(final IndicadorInventarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("INSERT INTO indicador_inventario (codigo, nombre) VALUES (?, ?)");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString())) {
            sentenciaPreparada.setObject(1, entity.getCodigo());
            sentenciaPreparada.setString(2, entity.getNombre());
            sentenciaPreparada.executeUpdate();
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se presentó un problema tratando de registrar el indicador de inventario.";
            var mensajeTecnico = "SQLException en 'create'. SQL=[" + sentenciaSQL + "], código=[" + entity.getCodigo() + "], nombre=[" + entity.getNombre() + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public void delete(final UUID id) throws DataFondaControlException {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }

        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("DELETE FROM indicador_inventario WHERE codigo = ?");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString())) {
            sentenciaPreparada.setObject(1, id);
            sentenciaPreparada.executeUpdate();
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "No fue posible eliminar el indicador de inventario.";
            var mensajeTecnico = "SQLException en 'delete'. SQL=[" + sentenciaSQL + "], ID=[" + id + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public List<IndicadorInventarioEntity> listByFilter(final IndicadorInventarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        var resultados = new ArrayList<IndicadorInventarioEntity>();
        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT codigo, nombre FROM indicador_inventario WHERE nombre LIKE ?");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString())) {
            sentenciaPreparada.setString(1, "%" + entity.getNombre() + "%");

            try (final var resultado = sentenciaPreparada.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(mapToEntity(resultado));
                }
            }
        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se presentó un problema filtrando indicadores de inventario.";
            var mensajeTecnico = "SQLException en 'listByFilter'. SQL=[" + sentenciaSQL + "], filtro=[" + entity.getNombre() + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return resultados;
    }

    @Override
    public List<IndicadorInventarioEntity> listAll() throws DataFondaControlException {
        var listaResultados = new ArrayList<IndicadorInventarioEntity>();
        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT codigo, nombre FROM indicador_inventario");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString());
             final var resultado = sentenciaPreparada.executeQuery()) {

            while (resultado.next()) {
                listaResultados.add(mapToEntity(resultado));
            }

        } catch (final SQLException excepcion) {
            var mensajeUsuario = "Se presentó un problema listando todos los indicadores de inventario.";
            var mensajeTecnico = "SQLException en 'listAll'. SQL=[" + sentenciaSQL + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return listaResultados;
    }

    @Override
    public List<IndicadorInventarioEntity> listByCodigo(UUID codigo) throws DataFondaControlException {
        var resultados = new ArrayList<IndicadorInventarioEntity>();
        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString())) {
            sentenciaPreparada.setObject(1, codigo);

            try (var resultado = sentenciaPreparada.executeQuery()) {
                while (resultado.next()) {
                    resultados.add(mapToEntity(resultado));
                }
            }

        } catch (final SQLException excepcion) {
            var mensajeUsuario = "No fue posible obtener el indicador de inventario por código.";
            var mensajeTecnico = "SQLException en 'listByCodigo'. SQL=[" + sentenciaSQL + "], código=[" + codigo + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        } catch (Exception exception) {
            var mensajeUsuario = "Ocurrió un error inesperado al obtener el indicador de inventario por código.";
            var mensajeTecnico = "Error inesperado en 'listByCodigo'. SQL=[" + sentenciaSQL + "], código=[" + codigo + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, exception);
        }

        return resultados;
    }

    @Override
    public IndicadorInventarioEntity findById(final UUID codigo) throws DataFondaControlException {
        var entidadRetorno = new IndicadorInventarioEntity();
        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("SELECT codigo, nombre FROM indicador_inventario WHERE codigo = ?");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString())) {
            sentenciaPreparada.setObject(1, codigo);

            try (final var resultado = sentenciaPreparada.executeQuery()) {
                if (resultado.next()) {
                    entidadRetorno = mapToEntity(resultado);
                }
            }

        } catch (final SQLException excepcion) {
            var mensajeUsuario = "No fue posible encontrar el indicador de inventario.";
            var mensajeTecnico = "SQLException en 'findById'. SQL=[" + sentenciaSQL + "], ID=[" + codigo + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }

        return entidadRetorno;
    }

    @Override
    public void update(final UUID codigo, final IndicadorInventarioEntity entity) throws DataFondaControlException {
        validarEntidad(entity);

        var sentenciaSQL = new StringBuilder();
        sentenciaSQL.append("UPDATE indicador_inventario SET nombre = ? WHERE codigo = ?");

        try (final var sentenciaPreparada = connection.prepareStatement(sentenciaSQL.toString())) {
            sentenciaPreparada.setString(1, entity.getNombre());
            sentenciaPreparada.setObject(2, codigo);

            if (sentenciaPreparada.executeUpdate() == 0) {
                throw DataFondaControlException.reportar(
                        "No se encontró el indicador para actualizar.",
                        "No hay registro con el código=[" + codigo + "] en la base de datos."
                );
            }

        } catch (final SQLException excepcion) {
            var mensajeUsuario = "No fue posible actualizar el indicador de inventario.";
            var mensajeTecnico = "SQLException en 'update'. SQL=[" + sentenciaSQL + "], código=[" + codigo + "].";
            throw DataFondaControlException.reportar(mensajeUsuario, mensajeTecnico, excepcion);
        }
    }

    @Override
    public void update(final List<IndicadorInventarioEntity> entities) throws DataFondaControlException {
        if (entities == null || entities.isEmpty()) {
            throw new IllegalArgumentException("La lista de entidades no puede ser nula ni vacía.");
        }

        for (IndicadorInventarioEntity entity : entities) {
            update(entity.getCodigo(), entity);
        }
    }

    private IndicadorInventarioEntity mapToEntity(final ResultSet rs) throws SQLException {
        return new IndicadorInventarioEntity(
                UtilUUID.convertirAUUID(rs.getString("codigo")),
                rs.getString("nombre")
        );
    }

    private void validarEntidad(final IndicadorInventarioEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula.");
        }
        if (UtilTexto.getInstancia().esNula(entity.getNombre())) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        if (entity.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre del indicador no puede exceder los 50 caracteres.");
        }
    }
}
