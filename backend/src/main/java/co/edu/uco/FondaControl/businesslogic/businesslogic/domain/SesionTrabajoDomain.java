package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class SesionTrabajoDomain {
    private UUID codigo;
    private UsuarioDomain usuario;
    private BigDecimal baseCaja;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;

    public SesionTrabajoDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setUsuario(UsuarioDomain.obtenerValorDefecto());
        setBaseCaja(UtilMoneda.obtenerValorDefecto());
        setFechaApertura(UtilFecha.obtenerValorDefecto());
        setFechaCierre(UtilFecha.obtenerValorDefecto());
    }

    public SesionTrabajoDomain(final UUID codigo, final UsuarioDomain usuario, final BigDecimal baseCaja,
                               final LocalDateTime fechaApertura, final LocalDateTime fechaCierre) {
        setCodigo(codigo);
        setUsuario(usuario);
        setBaseCaja(baseCaja);
        setFechaApertura(fechaApertura);
        setFechaCierre(fechaCierre);
    }

    public static SesionTrabajoDomain crearParaRegistro(final UsuarioDomain usuario, final BigDecimal baseCaja,
                                                        final LocalDateTime fechaApertura) {
        return new SesionTrabajoDomain(null, usuario, baseCaja, fechaApertura, null);
    }

    public static SesionTrabajoDomain obtenerValorDefecto() {
        return new SesionTrabajoDomain();
    }

    public static SesionTrabajoDomain obtenerValorDefecto(final SesionTrabajoDomain sesion) {
        return UtilObjeto.esNulo(sesion) ? obtenerValorDefecto() : sesion;
    }

    public UUID getCodigo() {
        return codigo;
    }

    private void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public UsuarioDomain getUsuario() {
        return usuario;
    }

    private void setUsuario(final UsuarioDomain usuario) {
        this.usuario = UtilObjeto.esNulo(usuario) ? UsuarioDomain.obtenerValorDefecto() : usuario;
    }

    public BigDecimal getBaseCaja() {
        return baseCaja;
    }

    private void setBaseCaja(final BigDecimal baseCaja) {
        this.baseCaja = UtilMoneda.asegurarNoNegativo(baseCaja);
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    private void setFechaApertura(final LocalDateTime fechaApertura) {
        this.fechaApertura = UtilFecha.asegurarFechaValida(fechaApertura);
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    private void setFechaCierre(final LocalDateTime fechaCierre) {
        this.fechaCierre = UtilFecha.asegurarFechaValida(fechaCierre);
    }
}
