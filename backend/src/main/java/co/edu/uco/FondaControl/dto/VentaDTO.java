package co.edu.uco.FondaControl.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class VentaDTO {
    private UUID codigo;
    private LocalDateTime fecha;
    private double totalVenta;
    private UUID codigoFormaPago;
    private UUID codigoTipoVenta;
    private UUID codigoSesionTrabajo;
    private UUID codigoMesa;

    private VentaDTO() {
        this.codigo = UtilUUID.obtenerValorDefecto();
        this.fecha = LocalDateTime.now();
        this.totalVenta = 0.0;
        this.codigoFormaPago = UtilUUID.obtenerValorDefecto();
        this.codigoTipoVenta = UtilUUID.obtenerValorDefecto();
        this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto();
        this.codigoMesa = UtilUUID.obtenerValorDefecto();
    }

    private VentaDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setFecha(builder.fecha);
        setTotalVenta(builder.totalVenta);
        setCodigoFormaPago(builder.codigoFormaPago);
        setCodigoTipoVenta(builder.codigoTipoVenta);
        setCodigoSesionTrabajo(builder.codigoSesionTrabajo);
        setCodigoMesa(builder.codigoMesa);
    }

    public static VentaDTO obtenerValorDefecto() {
        return builder().crear();
    }

    public static VentaDTO obtenerValorDefecto(final VentaDTO entidad) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(entidad, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(final LocalDateTime fecha) {
        this.fecha = fecha == null ? LocalDateTime.now() : fecha;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(final double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public UUID getCodigoFormaPago() {
        return codigoFormaPago;
    }

    public void setCodigoFormaPago(final UUID codigoFormaPago) {
        this.codigoFormaPago = UtilUUID.obtenerValorDefecto(codigoFormaPago);
    }

    public UUID getCodigoTipoVenta() {
        return codigoTipoVenta;
    }

    public void setCodigoTipoVenta(final UUID codigoTipoVenta) {
        this.codigoTipoVenta = UtilUUID.obtenerValorDefecto(codigoTipoVenta);
    }

    public UUID getCodigoSesionTrabajo() {
        return codigoSesionTrabajo;
    }

    public void setCodigoSesionTrabajo(final UUID codigoSesionTrabajo) {
        this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto(codigoSesionTrabajo);
    }

    public UUID getCodigoMesa() {
        return codigoMesa;
    }

    public void setCodigoMesa(final UUID codigoMesa) {
        this.codigoMesa = UtilUUID.obtenerValorDefecto(codigoMesa);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo;
        private LocalDateTime fecha;
        private double totalVenta;
        private UUID codigoFormaPago;
        private UUID codigoTipoVenta;
        private UUID codigoSesionTrabajo;
        private UUID codigoMesa;

        private Builder() {
            this.codigo = UtilUUID.obtenerValorDefecto();
            this.fecha = LocalDateTime.now();
            this.totalVenta = 0.0;
            this.codigoFormaPago = UtilUUID.obtenerValorDefecto();
            this.codigoTipoVenta = UtilUUID.obtenerValorDefecto();
            this.codigoSesionTrabajo = UtilUUID.obtenerValorDefecto();
            this.codigoMesa = UtilUUID.obtenerValorDefecto();
        }

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder fecha(final LocalDateTime fecha) {
            this.fecha = fecha;
            return this;
        }

        public Builder totalVenta(final double totalVenta) {
            this.totalVenta = totalVenta;
            return this;
        }

        public Builder codigoFormaPago(final UUID codigoFormaPago) {
            this.codigoFormaPago = codigoFormaPago;
            return this;
        }

        public Builder codigoTipoVenta(final UUID codigoTipoVenta) {
            this.codigoTipoVenta = codigoTipoVenta;
            return this;
        }

        public Builder codigoSesionTrabajo(final UUID codigoSesionTrabajo) {
            this.codigoSesionTrabajo = codigoSesionTrabajo;
            return this;
        }

        public Builder codigoMesa(final UUID codigoMesa) {
            this.codigoMesa = codigoMesa;
            return this;
        }

        public VentaDTO crear() {
            return new VentaDTO(this);
        }
    }
}
