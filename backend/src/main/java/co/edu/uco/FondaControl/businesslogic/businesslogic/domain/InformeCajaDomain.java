package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
public final class InformeCajaDomain {

    private UUID codigo;
    private UUID codigoSesionTrabajo;
    private LocalDate fecha;
    private BigDecimal totalVenta;
    private BigDecimal pagoEfectivo;
    private BigDecimal pagoTransferencia;

    public InformeCajaDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setCodigoSesionTrabajo(UtilUUID.obtenerValorDefecto());
        setFecha(UtilFecha.obtenerValorDefecto().toLocalDate());
        setTotalVenta(UtilMoneda.obtenerValorDefecto());
        setPagoEfectivo(UtilMoneda.obtenerValorDefecto());
        setPagoTransferencia(UtilMoneda.obtenerValorDefecto());
    }

    public InformeCajaDomain(final UUID codigo, final UUID codigoSesionTrabajo, final LocalDate fecha,
                             final BigDecimal totalVenta, final BigDecimal pagoEfectivo, final BigDecimal pagoTransferencia) {
        setCodigo(codigo);
        setCodigoSesionTrabajo(codigoSesionTrabajo);
        setFecha(fecha);
        setTotalVenta(totalVenta);
        setPagoEfectivo(pagoEfectivo);
        setPagoTransferencia(pagoTransferencia);
    }

    public static InformeCajaDomain obtenerValorDefecto() {
        return new InformeCajaDomain();
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

    private void setFecha(final LocalDate fecha) {
        this.fecha = UtilObjeto.getInstancia().esNulo(fecha) ? UtilFecha.obtenerValorDefecto().toLocalDate() : fecha;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
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
