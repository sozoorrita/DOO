package FondaControl.src.main.java.co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.entity.UsuarioEntity;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class SesionTrabajoDomain {
    private UUID codigo;
    private UsuarioEntity idUsuario;
    private BigDecimal baseCaja;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;

    SesionTrabajoDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setIdUsuario(UsuarioEntity.obtenerValorDefecto());
        setBaseCaja(UtilMoneda.obtenerValorDefecto());
        setFechaApertura(UtilFecha.obtenerValorDefecto());
        setFechaCierre(UtilFecha.obtenerValorDefecto());
    }

    public SesionTrabajoDomain(final UUID codigo, final UsuarioEntity idUsuario, final BigDecimal baseCaja, final LocalDateTime fechaApertura, final LocalDateTime fechaCierre) {
        setCodigo(codigo);
        setIdUsuario(idUsuario);
        setBaseCaja(baseCaja);
        setFechaApertura(fechaApertura);
        setFechaCierre(fechaCierre);
    }

    static SesionTrabajoDomain obtenerValorDefecto() {
        return new SesionTrabajoDomain();
    }

    static SesionTrabajoDomain obtenerValorDefecto(final SesionTrabajoDomain sesion) {
        return (sesion != null) ? sesion : obtenerValorDefecto();
    }

    public UUID getCodigo() {
        return codigo;
    }

    private void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public UsuarioEntity getIdUsuario() {
        return idUsuario;
    }

    private void setIdUsuario(final UsuarioEntity idUsuario) {
        this.idUsuario = (idUsuario != null) ? idUsuario : UsuarioEntity.obtenerValorDefecto();
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