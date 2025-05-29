package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class DetalleVentaDomain {
	private UUID codigoDetalleVenta;
	private double precioAplicado;
	private int cantidad;
	private ProductoDomain producto;
	private UUID codigoVenta;

	public DetalleVentaDomain() {
		setCodigoDetalleVenta(UtilUUID.obtenerValorDefecto());
		setPrecioAplicado(0.0);
		setCantidad(0);
		setProducto(null);
		setCodigoVenta(UtilUUID.obtenerValorDefecto());
	}

	public DetalleVentaDomain(final UUID codigoDetalleVenta, final ProductoDomain producto, final UUID codigoVenta,
			final double precioAplicado, final int cantidad) {
		setCodigoDetalleVenta(codigoDetalleVenta);
		setProducto(producto);
		setCodigoVenta(codigoVenta);
		setPrecioAplicado(precioAplicado);
		setCantidad(cantidad);
	}

	public static DetalleVentaDomain obtenerValorDefecto() {
		return new DetalleVentaDomain();
	}

	static DetalleVentaDomain obtenerValorDefecto(final DetalleVentaDomain entidad) {
		return entidad != null ? entidad : obtenerValorDefecto();
	}

	public UUID getCodigoDetalleVenta() {
		return codigoDetalleVenta;
	}

	public void setCodigoDetalleVenta(final UUID codigoDetalleVenta) {
		this.codigoDetalleVenta = UtilUUID.obtenerValorDefecto(codigoDetalleVenta);
	}

	public double getPrecioAplicado() {
		return precioAplicado;
	}

	public void setPrecioAplicado(final double precioAplicado) {
		this.precioAplicado = precioAplicado >= 0.0 ? precioAplicado : 0.0;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(final int cantidad) {
		this.cantidad = cantidad >= 0 ? cantidad : 0;
	}

	public ProductoDomain getProducto() {
		return producto;
	}

	public void setProducto(ProductoDomain producto) {
		this.producto = producto;
	}

	public UUID getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(UUID codigoVenta) {
		this.codigoVenta = codigoVenta;
	}
}
