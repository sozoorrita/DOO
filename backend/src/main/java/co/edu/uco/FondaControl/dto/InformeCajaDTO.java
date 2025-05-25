package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class InformeCajaDTO {

    private UUID codigo;
    private UUID codigoSesionTrabajo;
    private LocalDate fecha;
    private BigDecimal totalVenta;
    private BigDecimal pagoEfectivo;
    private BigDecimal pagoTransferencia;

    public InformeCajaDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setCodigoSesionTrabajo(UtilUUID.obtenerValorDefecto());
        setFecha(UtilFecha.obtenerValorDefecto().toLocalDate());
        setTotalVenta(UtilMoneda.obtenerValorDefecto());
        setPagoEfectivo(UtilMoneda.obtenerValorDefecto());
        setPagoTransferencia(UtilMoneda.obtenerValorDefecto());
    }

    public InformeCajaDTO(final UUID codigo, final UUID codigoSesionTrabajo, final LocalDate fecha,
                          final BigDecimal totalVenta, final BigDecimal pagoEfectivo, final BigDecimal pagoTransferencia) {
        setCodigo(codigo);
        setCodigoSesionTrabajo(codigoSesionTrabajo);
        setFecha(fecha);
        setTotalVenta(totalVenta);
        setPagoEfectivo(pagoEfectivo);
        setPagoTransferencia(pagoTransferencia);
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public UUID getCodigoSesionTrabajo() {
        return codigoSesionTrabajo;
    }

    public void setCodigoSesionTrabajo(final UUID codigoSesionTrabajo) {
        this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto(codigoSesionTrabajo);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDate fecha) {
        this.fecha = (fecha != null) ? fecha : UtilFecha.obtenerValorDefecto().toLocalDate();
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(final BigDecimal totalVenta) {
        this.totalVenta = UtilMoneda.obtenerValorDefecto(totalVenta);
    }

    public BigDecimal getPagoEfectivo() {
        return pagoEfectivo;
    }

    public void setPagoEfectivo(final BigDecimal pagoEfectivo) {
        this.pagoEfectivo = UtilMoneda.obtenerValorDefecto(pagoEfectivo);
    }

    public BigDecimal getPagoTransferencia() {
        return pagoTransferencia;
    }

    public void setPagoTransferencia(final BigDecimal pagoTransferencia) {
        this.pagoTransferencia = UtilMoneda.obtenerValorDefecto(pagoTransferencia);
    }
}
