package co.edu.uco.FondaControl.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class SesionTrabajoDTO {
    private UUID codigo;
    private UUID idUsuario;
    private BigDecimal baseCaja;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;

    public SesionTrabajoDTO() {
        this.codigo = UUID.randomUUID();
        this.idUsuario = UUID.randomUUID();
        this.baseCaja = BigDecimal.ZERO;
        this.fechaApertura = LocalDateTime.now();
        this.fechaCierre = LocalDateTime.now();
    }

    public SesionTrabajoDTO(UUID codigo, UUID idUsuario, BigDecimal baseCaja, LocalDateTime fechaApertura, LocalDateTime fechaCierre) {
        this.codigo = codigo;
        this.idUsuario = idUsuario;
        this.baseCaja = baseCaja;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = codigo;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getBaseCaja() {
        return baseCaja;
    }

    public void setBaseCaja(BigDecimal baseCaja) {
        this.baseCaja = baseCaja;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}