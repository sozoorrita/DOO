package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class InventarioDTO {

    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private UUID codigoIndicador;
    private String nombreIndicador;

    public InventarioDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
        setNombreIndicador(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public InventarioDTO(final UUID codigo) {
        setCodigo(codigo);
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
        setNombreIndicador(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public InventarioDTO(final UUID codigo, final String nombreProducto, final int cantidad,
                         final UUID codigoIndicador, final String nombreIndicador) {
        setCodigo(codigo);
        setNombreProducto(nombreProducto);
        setCantidad(cantidad);
        setCodigoIndicador(codigoIndicador);
        setNombreIndicador(nombreIndicador);
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

    public String getNombreIndicador() {
        return nombreIndicador;
    }

    public void setNombreIndicador(final String nombreIndicador) {
        this.nombreIndicador = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreIndicador);
    }
}
