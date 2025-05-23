package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.util.UUID;

public final class InventarioDTO {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private UUID codigoIndicador;
    private String nombreIndicador;

    public InventarioDTO() {
        this.codigo = UtilUUID.obtenerValorDefecto();
        this.nombreProducto = UtilTexto.getInstancia().obtenerValorDefecto();
        this.cantidad = 0;
        this.codigoIndicador = UtilUUID.obtenerValorDefecto();
        this.nombreIndicador = UtilTexto.getInstancia().obtenerValorDefecto();
    }

    public InventarioDTO(final UUID codigo, final String nombreProducto, final int cantidad,
                         final UUID codigoIndicador, final String nombreIndicador) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
        this.nombreProducto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreProducto);
        this.cantidad = Math.max(cantidad, 0);
        this.codigoIndicador = UtilUUID.obtenerValorDefecto(codigoIndicador);
        this.nombreIndicador = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreIndicador);
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
        this.cantidad = Math.max(cantidad, 0);
    }

    public UUID getCodigoIndicador() {
        return codigoIndicador;
    }

    public void setCodigoIndicador(final UUID codigoIndicador) {
        this.codigoIndicador = UtilUUID.obtenerValorDefecto(codigoIndicador);
    }

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(final String nombreIndicador) {
        this.nombreIndicador = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreIndicador);
    }
}
