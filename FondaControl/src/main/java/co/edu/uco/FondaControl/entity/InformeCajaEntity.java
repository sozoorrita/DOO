package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.utilitarios.UtilMoneda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class InformeCajaEntity {
    private UUID codigo;
    private UUID codigoSesionTrabajo;
    private LocalDate fecha;
    private BigDecimal totalVenta;
    private BigDecimal pagoEfectivo;
    private BigDecimal pagoTransferencia;

    public InformeCajaEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setCodigoSesionTrabajo(UtilUUID.obtenerValorDefecto());
        setFecha(UtilFecha.obtenerValorDefecto().toLocalDate());
        setTotalVenta(UtilMoneda.obtenerValorDefecto());
        setPagoEfectivo(UtilMoneda.obtenerValorDefecto());
        setPagoTransferencia(UtilMoneda.obtenerValorDefecto());
    }

    public InformeCajaEntity(final UUID codigo, final UUID codigoSesionTrabajo, final LocalDate fecha, final BigDecimal totalVenta, final BigDecimal pagoEfectivo, final BigDecimal pagoTransferencia) {
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
        this.totalVenta = UtilMoneda.asegurarNoNegativo(totalVenta);
    }

    public BigDecimal getPagoEfectivo() {
        return pagoEfectivo;
    }

    public void setPagoEfectivo(final BigDecimal pagoEfectivo) {
        this.pagoEfectivo = UtilMoneda.asegurarNoNegativo(pagoEfectivo);
    }

    public BigDecimal getPagoTransferencia() {
        return pagoTransferencia;
    }

    public void setPagoTransferencia(final BigDecimal pagoTransferencia) {
        this.pagoTransferencia = UtilMoneda.asegurarNoNegativo(pagoTransferencia);
    }
}