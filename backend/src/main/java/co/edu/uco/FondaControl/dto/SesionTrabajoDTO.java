package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class SesionTrabajoDTO {
    private UUID codigo;
    private UUID idUsuario;
    private String nombreUsuario;
    private BigDecimal baseCaja;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaCierre;

    public SesionTrabajoDTO() {
        this.codigo = null; // UUID será asignado por la base de datos
        this.idUsuario = UtilUUID.obtenerValorDefecto();
        this.nombreUsuario = UtilTexto.getInstancia().obtenerValorDefecto();
        this.baseCaja = UtilMoneda.obtenerValorDefecto();
        this.fechaApertura = UtilFecha.obtenerValorDefecto();
        this.fechaCierre = null; // Puede ser nulo al momento de crear
    }

    public SesionTrabajoDTO(UUID codigo, UUID idUsuario, String nombreUsuario, BigDecimal baseCaja,
                            LocalDateTime fechaApertura, LocalDateTime fechaCierre) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
        this.idUsuario = UtilUUID.obtenerValorDefecto(idUsuario);
        this.nombreUsuario = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreUsuario);
        this.baseCaja = UtilMoneda.obtenerValorDefecto(baseCaja);
        this.fechaApertura = UtilFecha.obtenerValorDefecto(fechaApertura);
        this.fechaCierre = UtilFecha.obtenerValorDefecto(fechaCierre);
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = UtilUUID.obtenerValorDefecto(idUsuario);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreUsuario);
    }

    public BigDecimal getBaseCaja() {
        return baseCaja;
    }

    public void setBaseCaja(BigDecimal baseCaja) {
        this.baseCaja = UtilMoneda.obtenerValorDefecto(baseCaja);
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = UtilFecha.obtenerValorDefecto(fechaApertura);
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = UtilFecha.obtenerValorDefecto(fechaCierre);
    }
}
