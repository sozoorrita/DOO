package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class DetalleVentaDTO {
    private UUID codigo;
    private double precioAplicado;
    private int cantidad;
    private ProductoDTO producto; // <--- Ahora es ProductoDTO
    private UUID codigoVenta;

    public DetalleVentaDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setPrecioAplicado(0.0);
        setCantidad(0);
        setProducto(null);
        setCodigoVenta(UtilUUID.obtenerValorDefecto());
    }

    public DetalleVentaDTO(UUID codigo, ProductoDTO producto, UUID codigoVenta, double precioAplicado, int cantidad) {
        setCodigo(codigo);
        setProducto(producto);
        setCodigoVenta(codigoVenta);
        setPrecioAplicado(precioAplicado);
        setCantidad(cantidad);
    }

    private DetalleVentaDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setPrecioAplicado(builder.precioAplicado);
        setCantidad(builder.cantidad);
        setProducto(builder.producto);
        setCodigoVenta(builder.codigoVenta);
    }

    public static DetalleVentaDTO obtenerValorDefecto() {
        return builder().crear();
    }

    public static DetalleVentaDTO obtenerValorDefecto(final DetalleVentaDTO entidad) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(entidad, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public double getPrecioAplicado() {
        return precioAplicado;
    }

    public void setPrecioAplicado(final double precioAplicado) {
        this.precioAplicado = precioAplicado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public UUID getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(UUID codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo = UtilUUID.obtenerValorDefecto();
        private double precioAplicado = 0.0;
        private int cantidad = 0;
        private ProductoDTO producto = null;
        private UUID codigoVenta = UtilUUID.obtenerValorDefecto();

        private Builder() {}

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder precioAplicado(final double precioAplicado) {
            this.precioAplicado = precioAplicado;
            return this;
        }

        public Builder cantidad(final int cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Builder producto(final ProductoDTO producto) {
            this.producto = producto;
            return this;
        }

        public Builder codigoVenta(final UUID codigoVenta) {
            this.codigoVenta = codigoVenta;
            return this;
        }

        public DetalleVentaDTO crear() {
            return new DetalleVentaDTO(this);
        }
    }
}
