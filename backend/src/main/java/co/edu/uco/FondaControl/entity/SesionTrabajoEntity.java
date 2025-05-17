package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class SesionTrabajoEntity {
    private UUID codigo;
    private UsuarioEntity idUsuario;
    private BigDecimal baseCaja;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;

    public SesionTrabajoEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setIdUsuario(UsuarioEntity.obtenerValorDefecto());
        setBaseCaja(UtilMoneda.obtenerValorDefecto());
        setFechaApertura(UtilFecha.obtenerValorDefecto());
        setFechaCierre(UtilFecha.obtenerValorDefecto());
    }

    public SesionTrabajoEntity(final UUID codigo, final UsuarioEntity idUsuario, final BigDecimal baseCaja, final LocalDateTime fechaApertura, final LocalDateTime fechaCierre) {
        setCodigo(codigo);
        setIdUsuario(idUsuario);
        setBaseCaja(baseCaja);
        setFechaApertura(fechaApertura);
        setFechaCierre(fechaCierre);
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public UsuarioEntity getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final UsuarioEntity idUsuario) {
        this.idUsuario = (idUsuario != null) ? idUsuario : UsuarioEntity.obtenerValorDefecto();
    }

    public BigDecimal getBaseCaja() {
        return baseCaja;
    }

    public void setBaseCaja(final BigDecimal baseCaja) {
        this.baseCaja = UtilMoneda.asegurarNoNegativo(baseCaja);
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(final LocalDateTime fechaApertura) {
        this.fechaApertura = UtilFecha.asegurarFechaValida(fechaApertura);
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(final LocalDateTime fechaCierre) {
        this.fechaCierre = UtilFecha.asegurarFechaValida(fechaCierre);
    }
}
