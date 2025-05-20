package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class InventarioDTO {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private UUID codigoIndicador;
    private String nombreIndicador;

    public InventarioDTO() {
        setId(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
        setNombreIndicador(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public InventarioDTO(final UUID id, String nombreProducto, int cantidad, IndicadorInventarioEntity indicador) {
        setId(id);
        setNombreProducto(nombreProducto);
        setCantidad(cantidad);
        setCodigoIndicador(indicador.getCodigo());
        setNombreIndicador(indicador.getNombre());
    }

    public UUID getId() {
        return codigo;
    }

    public void setId(final UUID id) {
        this.codigo = UtilUUID.obtenerValorDefecto(id);
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
        this.cantidad = cantidad;
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