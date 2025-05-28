package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class VentaDomain {
	private UUID codigoVenta;
	private LocalDateTime fecha;
	private double totalVenta;
	private UUID codigoFormaPago;
	private UUID codigoTipoVenta;
	private UUID codigoSesionTrabajo;
	private UUID codigoMesa;

	VentaDomain() {
		setCodigoVenta(UtilUUID.obtenerValorDefecto());
		setFecha(LocalDateTime.now());
		setTotalVenta(0.0);
		setCodigoFormaPago(UtilUUID.obtenerValorDefecto());
		setCodigoTipoVenta(UtilUUID.obtenerValorDefecto());
		setCodigoSesionTrabajo(UtilUUID.obtenerValorDefecto());
		setCodigoMesa(UtilUUID.obtenerValorDefecto());
	}

	public VentaDomain(final UUID codigoVenta, final LocalDateTime fecha, final double totalVenta,
			final UUID codigoFormaPago, final UUID codigoTipoVenta, final UUID codigoSesionTrabajo,
			final UUID codigoMesa) {
		setCodigoVenta(codigoVenta);
		setFecha(fecha);
		setTotalVenta(totalVenta);
		setCodigoFormaPago(codigoFormaPago);
		setCodigoTipoVenta(codigoTipoVenta);
		setCodigoSesionTrabajo(codigoSesionTrabajo);
		setCodigoMesa(codigoMesa);
	}

	public static VentaDomain obtenerValorDefecto() {
		return new VentaDomain();
	}

	static VentaDomain obtenerValorDefecto(final VentaDomain entidad) {
		return entidad != null ? entidad : obtenerValorDefecto();
	}

	public UUID getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(final UUID codigoVenta) {
		this.codigoVenta = UtilUUID.obtenerValorDefecto(codigoVenta);
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	private void setFecha(final LocalDateTime fecha) {
		this.fecha = fecha != null ? fecha : LocalDateTime.now();
	}

	public double getTotalVenta() {
		return totalVenta;
	}

	private void setTotalVenta(final double totalVenta) {
		this.totalVenta = totalVenta >= 0.0 ? totalVenta : 0.0;
	}

	public UUID getCodigoFormaPago() {
		return codigoFormaPago;
	}

	private void setCodigoFormaPago(final UUID codigoFormaPago) {
		this.codigoFormaPago = UtilUUID.obtenerValorDefecto(codigoFormaPago);
	}

	public UUID getCodigoTipoVenta() {
		return codigoTipoVenta;
	}

	private void setCodigoTipoVenta(final UUID codigoTipoVenta) {
		this.codigoTipoVenta = UtilUUID.obtenerValorDefecto(codigoTipoVenta);
	}

	public UUID getCodigoSesionTrabajo() {
		return codigoSesionTrabajo;
	}

	private void setCodigoSesionTrabajo(final UUID codigoSesionTrabajo) {
		this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto(codigoSesionTrabajo);
	}

	public UUID getCodigoMesa() {
		return codigoMesa;
	}

	private void setCodigoMesa(final UUID codigoMesa) {
		this.codigoMesa = UtilUUID.obtenerValorDefecto(codigoMesa);
	}
}
