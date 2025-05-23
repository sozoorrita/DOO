package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;


import java.util.UUID;

public final class InventarioEntity {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private UUID codigoIndicador;

    public InventarioEntity(UUID codigo, String nombreProducto, int cantidad, UUID codigoIndicador) {
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
        this.cantidad = Math.max(0, cantidad); // Evitar valores negativos
    }

    public UUID getCodigoIndicador() {
        return codigoIndicador;
    }

    public void setCodigoIndicador(final UUID codigoIndicador) {
        this.codigoIndicador = UtilUUID.obtenerValorDefecto(codigoIndicador);
    }
}
