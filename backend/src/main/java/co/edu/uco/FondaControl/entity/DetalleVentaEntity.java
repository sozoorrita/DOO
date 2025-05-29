package co.edu.uco.FondaControl.entity;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class DetalleVentaEntity {
	private UUID codigo;
	private double precioAplicado;
	private int cantidad;
	private ProductoEntity producto;
	private UUID codigoVenta;

	public DetalleVentaEntity() {
		setCodigo(UtilUUID.obtenerValorDefecto());
		setPrecioAplicado(0.0);
		setCantidad(0);
		setProducto(null);
		setCodigoVenta(UtilUUID.obtenerValorDefecto());
	}

	private DetalleVentaEntity(final Builder builder) {
		setCodigo(builder.codigo);
		setPrecioAplicado(builder.precioAplicado);
		setCantidad(builder.cantidad);
		setProducto(builder.producto);
		setCodigoVenta(builder.codigoVenta);
	}

	public static DetalleVentaEntity obtenerValorDefecto() {
		return builder().crear();
	}

	public static DetalleVentaEntity obtenerValorDefecto(final DetalleVentaEntity entidad) {
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

	public ProductoEntity getProducto() {
		return producto;
	}

	public void setProducto(final ProductoEntity producto) {
	    this.producto = UtilObjeto.getInstancia().obtenerValorDefecto(producto, null);
	}

	public UUID getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(final UUID codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private UUID codigo = UtilUUID.obtenerValorDefecto();
		private double precioAplicado = 0.0;
		private int cantidad = 0;
		private ProductoEntity producto = null;
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

		public Builder producto(final ProductoEntity producto) {
			this.producto = producto;
			return this;
		}

		public Builder codigoVenta(final UUID codigoVenta) {
			this.codigoVenta = codigoVenta;
			return this;
		}

		public DetalleVentaEntity crear() {
			return new DetalleVentaEntity(this);
		}
	}

	public UUID getCodigoDetalleVenta() {
		return codigo;
	}
}
