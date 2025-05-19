package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public class DetalleVentaDTO {

	private UUID codigoDetalleVenta;
	private String nombreProducto;
	private double precioAplicado;
	private int cantidad;

	public DetalleVentaDTO() {
		setCodigoDetalleVenta(UtilUUID.obtenerValorDefecto());
		setNombreProducto(UtilTexto.obtenerValorDefecto());
	}

	public DetalleVentaDTO(final UUID codigoDetalleVenta) {
		setCodigoDetalleVenta(codigoDetalleVenta);
		setNombreProducto(UtilTexto.obtenerValorDefecto());
	}

	public DetalleVentaDTO(final UUID codigoDetalleVenta, final String nombreProducto) {
		setCodigoDetalleVenta(codigoDetalleVenta);
		setNombreProducto(nombreProducto);
	}

	public UUID getCodigoDetalleVenta() {
		return codigoDetalleVenta;
	}

	public void setCodigoDetalleVenta(UUID codigoDetalleVenta) {
		this.codigoDetalleVenta = codigoDetalleVenta;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public double getPrecioAplicado() {
		return precioAplicado;
	}

	public void setPrecioAplicado(double precioAplicado) {
		this.precioAplicado = precioAplicado;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
