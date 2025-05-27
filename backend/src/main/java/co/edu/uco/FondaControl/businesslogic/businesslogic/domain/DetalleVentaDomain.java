package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class DetalleVentaDomain {
	private UUID codigoDetalleVenta;
	private String nombreProducto;
	private double precioAplicado;
	private int cantidad;

	DetalleVentaDomain() {
		setCodigoDetalleVenta(UtilUUID.obtenerValorDefecto());
		setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
		setPrecioAplicado(0.0);
		setCantidad(0);
	}

	public DetalleVentaDomain(final UUID codigoDetalleVenta, final String nombreProducto, final double precioAplicado,
			final int cantidad) {
		setCodigoDetalleVenta(codigoDetalleVenta);
		setNombreProducto(nombreProducto);
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

	public String getNombreProducto() {
		return nombreProducto;
	}

	private void setNombreProducto(final String nombreProducto) {
		this.nombreProducto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreProducto);
	}

	public double getPrecioAplicado() {
		return precioAplicado;
	}

	private void setPrecioAplicado(final double precioAplicado) {
		this.precioAplicado = precioAplicado >= 0.0 ? precioAplicado : 0.0;
	}

	public int getCantidad() {
		return cantidad;
	}

	private void setCantidad(final int cantidad) {
		this.cantidad = cantidad >= 0 ? cantidad : 0;
	}
}
