package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.util.UUID;

public final class InventarioEntity {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private UUID codigoIndicador;

    public InventarioEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
    }

    public InventarioEntity(final UUID codigo, final String nombreProducto, final int cantidad, final UUID codigoIndicador) {
        setCodigo(codigo);
        setNombreProducto(nombreProducto);
        setCantidad(cantidad);
        setCodigoIndicador(codigoIndicador);
    }

    public InventarioEntity(final UUID id) {
        setCodigo(id);
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
    }

    public static InventarioEntity obtenerValorDefecto() {
        return new InventarioEntity();
    }

    public static InventarioEntity obtenerValorDefecto(final InventarioEntity entidad) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(entidad, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(final String nombreProducto) {
        this.nombreProducto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreProducto);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = Math.max(0, cantidad);
    }

    public UUID getCodigoIndicador() {
        return codigoIndicador;
    }

    public void setCodigoIndicador(final UUID codigoIndicador) {
        this.codigoIndicador = UtilUUID.obtenerValorDefecto(codigoIndicador);
    }
}
