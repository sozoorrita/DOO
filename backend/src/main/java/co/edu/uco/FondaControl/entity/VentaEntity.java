package co.edu.uco.FondaControl.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class VentaEntity {

	private UUID codigoVenta;
	private LocalDateTime fecha;
	private double totalVenta;
	private UUID codigoFormaPago;
	private UUID codigoTipoVenta;
	private UUID codigoSesionTrabajo;
	private UUID codigoMesa;

	public VentaEntity() {
		this.codigoVenta = UUID.randomUUID();
		this.fecha = LocalDateTime.now();
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
