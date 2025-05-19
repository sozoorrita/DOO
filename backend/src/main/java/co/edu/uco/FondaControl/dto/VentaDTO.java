package co.edu.uco.FondaControl.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VentaDTO {

	private UUID codigoVenta;
	LocalDateTime fecha = LocalDateTime.now();
	private double totalVenta;
	private UUID codigoFormaPago;
	private UUID codigoTipoVenta;
	private UUID codigoSesionTrabajo;
	private UUID codigoMesa;

	VentaDTO() {
		setCodigoVenta(codigoVenta);
	}

	public UUID getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(UUID codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public UUID getCodigoFormaPago() {
		return codigoFormaPago;
	}

	public void setCodigoFormaPago(UUID codigoFormaPago) {
		this.codigoFormaPago = codigoFormaPago;
	}

	public UUID getCodigoTipoVenta() {
		return codigoTipoVenta;
	}

	public void setCodigoTipoVenta(UUID codigoTipoVenta) {
		this.codigoTipoVenta = codigoTipoVenta;
	}

	public UUID getCodigoSesionTrabajo() {
		return codigoSesionTrabajo;
	}

	public void setCodigoSesionTrabajo(UUID codigoSesionTrabajo) {
		this.codigoSesionTrabajo = codigoSesionTrabajo;
	}

	public UUID getCodigoMesa() {
		return codigoMesa;
	}

	public void setCodigoMesa(UUID codigoMesa) {
		this.codigoMesa = codigoMesa;
	}

}
