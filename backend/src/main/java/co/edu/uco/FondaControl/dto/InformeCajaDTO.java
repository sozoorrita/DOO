package co.edu.uco.FondaControl.dto;

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
        this.codigo = UUID.randomUUID();
        this.codigoSesionTrabajo = UUID.randomUUID();
        this.fecha = LocalDate.now();
        this.totalVenta = BigDecimal.ZERO;
        this.pagoEfectivo = BigDecimal.ZERO;
        this.pagoTransferencia = BigDecimal.ZERO;
    }

    public InformeCajaDTO(UUID codigo, UUID codigoSesionTrabajo, LocalDate fecha, BigDecimal totalVenta, BigDecimal pagoEfectivo, BigDecimal pagoTransferencia) {
        this.codigo = codigo;
        this.codigoSesionTrabajo = codigoSesionTrabajo;
        this.fecha = fecha;
        this.totalVenta = totalVenta;
        this.pagoEfectivo = pagoEfectivo;
        this.pagoTransferencia = pagoTransferencia;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = codigo;
    }

    public UUID getCodigoSesionTrabajo() {
        return codigoSesionTrabajo;
    }

    public void setCodigoSesionTrabajo(UUID codigoSesionTrabajo) {
        this.codigoSesionTrabajo = codigoSesionTrabajo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getPagoEfectivo() {
        return pagoEfectivo;
    }

    public void setPagoEfectivo(BigDecimal pagoEfectivo) {
        this.pagoEfectivo = pagoEfectivo;
    }

    public BigDecimal getPagoTransferencia() {
        return pagoTransferencia;
    }

    public void setPagoTransferencia(BigDecimal pagoTransferencia) {
        this.pagoTransferencia = pagoTransferencia;
    }
}
