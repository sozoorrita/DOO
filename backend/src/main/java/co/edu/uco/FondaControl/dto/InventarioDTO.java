package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class InventarioDTO {

    private UUID codigo;
    private ProductoDTO producto;
    private int cantidad;
    private UUID codigoIndicador;

    public InventarioDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setProducto(null);
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
    }

    public InventarioDTO(final UUID codigo) {
        setCodigo(codigo);
        setProducto(null);
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
    }

    public InventarioDTO(final UUID codigo, final ProductoDTO producto, final int cantidad, final UUID codigoIndicador) {
        setCodigo(codigo);
        setProducto(producto);
        setCantidad(cantidad);
        setCodigoIndicador(codigoIndicador);
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(final ProductoDTO producto) {
        this.producto = producto;
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
