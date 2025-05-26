package co.edu.uco.FondaControl.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class VentaEntity {
    private UUID codigo;
    private LocalDateTime fecha;
    private double totalVenta;
    private UUID codigoFormaPago;
    private UUID codigoTipoVenta;
    private UUID codigoSesionTrabajo;
    private UUID codigoMesa;

    private VentaEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setFecha(LocalDateTime.now());
        setTotalVenta(0.0);
        setCodigoFormaPago(UtilUUID.obtenerValorDefecto());
        setCodigoTipoVenta(UtilUUID.obtenerValorDefecto());
        setCodigoSesionTrabajo(UtilUUID.obtenerValorDefecto());
        setCodigoMesa(UtilUUID.obtenerValorDefecto());
    }

    private VentaEntity(final Builder builder) {
        setCodigo(builder.codigo);
        setFecha(builder.fecha);
        setTotalVenta(builder.totalVenta);
        setCodigoFormaPago(builder.codigoFormaPago);
        setCodigoTipoVenta(builder.codigoTipoVenta);
        setCodigoSesionTrabajo(builder.codigoSesionTrabajo);
        setCodigoMesa(builder.codigoMesa);
    }

    public static VentaEntity obtenerValorDefecto() {
        return builder().crear();
    }

    public static VentaEntity obtenerValorDefecto(final VentaEntity entidad) {
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
        private UUID codigo = UtilUUID.obtenerValorDefecto();
        private LocalDateTime fecha = LocalDateTime.now();
        private double totalVenta = 0.0;
        private UUID codigoFormaPago = UtilUUID.obtenerValorDefecto();
        private UUID codigoTipoVenta = UtilUUID.obtenerValorDefecto();
        private UUID codigoSesionTrabajo = UtilUUID.obtenerValorDefecto();
        private UUID codigoMesa = UtilUUID.obtenerValorDefecto();

        private Builder() {}

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

        public VentaEntity crear() {
            return new VentaEntity(this);
        }
    }
}
