package co.edu.uco.FondaControl.entity;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class InventarioEntity {
    private UUID codigo;
    private ProductoEntity producto;
    private int cantidad;
    private UUID codigoIndicador;

    public InventarioEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setProducto(null);
        setCantidad(0);
        setCodigoIndicador(UtilUUID.obtenerValorDefecto());
    }

    public InventarioEntity(final UUID codigo, final ProductoEntity producto, final int cantidad, final UUID codigoIndicador) {
        setCodigo(codigo);
        setProducto(producto);
        setCantidad(cantidad);
        setCodigoIndicador(codigoIndicador);
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

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(final ProductoEntity producto) {
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
