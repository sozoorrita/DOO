package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class IndicadorInventarioEntity {
    private UUID codigo;
    private String nombre;

    public IndicadorInventarioEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public IndicadorInventarioEntity(final UUID codigo, final String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
    }

    public IndicadorInventarioEntity(UUID codigoIndicador) {
        setCodigo(codigoIndicador);
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public static IndicadorInventarioEntity obtenerValorDefecto() {
        return new IndicadorInventarioEntity();
    }

    public static UUID obtenerCodigoDefecto(final IndicadorInventarioEntity indicador) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(indicador, obtenerValorDefecto()).getCodigo();
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
    }
}
