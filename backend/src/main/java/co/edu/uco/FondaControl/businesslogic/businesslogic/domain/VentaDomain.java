package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class VentaDomain {

    private final UUID codigoVenta;
    private final LocalDateTime fecha;
    private final double totalVenta;
    private final FormaPagoDomain formaPago;
    private final TipoVentaDomain tipoVenta;
    private final SesionTrabajoDomain sesionTrabajo;
    private final MesaDomain mesa;

    public VentaDomain(final UUID codigoVenta, final LocalDateTime fecha, final double totalVenta,
                      final FormaPagoDomain formaPago, final TipoVentaDomain tipoVenta,
                      final SesionTrabajoDomain sesionTrabajo, final MesaDomain mesa) {
        this.codigoVenta = UtilUUID.obtenerValorDefecto(codigoVenta);
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        this.totalVenta = totalVenta >= 0.0 ? totalVenta : 0.0;
        this.formaPago = formaPago != null ? formaPago : FormaPagoDomain.obtenerValorDefecto();
        this.tipoVenta = tipoVenta != null ? tipoVenta : TipoVentaDomain.obtenerValorDefecto();
        this.sesionTrabajo = sesionTrabajo != null ? sesionTrabajo : SesionTrabajoDomain.obtenerValorDefecto();
        this.mesa = mesa != null ? mesa : MesaDomain.obtenerValorDefecto();
    }

    public UUID getCodigoVenta() {
        return codigoVenta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public FormaPagoDomain getFormaPago() {
        return formaPago;
    }

    public TipoVentaDomain getTipoVenta() {
        return tipoVenta;
    }

    public SesionTrabajoDomain getSesionTrabajo() {
        return sesionTrabajo;
    }

    public MesaDomain getMesa() {
        return mesa;
    }

	public static VentaDomain obtenerValorDefecto() {
		// TODO Auto-generated method stub
		return null;
	}
}
