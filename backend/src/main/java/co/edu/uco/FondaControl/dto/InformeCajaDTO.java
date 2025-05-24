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
        this.codigo = null; // Deja que la base de datos lo genere
        this.codigoSesionTrabajo = UtilUUID.generarNuevoUUID(); // Este sí puedes generarlo
        this.fecha = UtilFecha.obtenerValorDefecto().toLocalDate();
        this.totalVenta = UtilMoneda.obtenerValorDefecto();
        this.pagoEfectivo = UtilMoneda.obtenerValorDefecto();
        this.pagoTransferencia = UtilMoneda.obtenerValorDefecto();
    }

    public InformeCajaDTO(UUID codigo, UUID codigoSesionTrabajo, LocalDate fecha, BigDecimal totalVenta, BigDecimal pagoEfectivo, BigDecimal pagoTransferencia) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo); // ya no se autogenera si llega null
        this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto(codigoSesionTrabajo);
        this.fecha = (fecha != null) ? fecha : UtilFecha.obtenerValorDefecto().toLocalDate();
        this.totalVenta = UtilMoneda.obtenerValorDefecto(totalVenta);
        this.pagoEfectivo = UtilMoneda.obtenerValorDefecto(pagoEfectivo);
        this.pagoTransferencia = UtilMoneda.obtenerValorDefecto(pagoTransferencia);
    }

    // Getters y Setters
    // ...



    // Getters y Setters seguros
    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public UUID getCodigoSesionTrabajo() {
        return codigoSesionTrabajo;
    }

    public void setCodigoSesionTrabajo(UUID codigoSesionTrabajo) {
        this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto(codigoSesionTrabajo);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = (fecha != null) ? fecha : UtilFecha.obtenerValorDefecto().toLocalDate();
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = UtilMoneda.obtenerValorDefecto(totalVenta);
    }

    public BigDecimal getPagoEfectivo() {
        return pagoEfectivo;
    }

    public void setPagoEfectivo(BigDecimal pagoEfectivo) {
        this.pagoEfectivo = UtilMoneda.obtenerValorDefecto(pagoEfectivo);
    }

    public BigDecimal getPagoTransferencia() {
        return pagoTransferencia;
    }

    public void setPagoTransferencia(BigDecimal pagoTransferencia) {
        this.pagoTransferencia = UtilMoneda.obtenerValorDefecto(pagoTransferencia);
    }
}
