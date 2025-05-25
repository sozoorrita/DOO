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
        setCodigo(UtilUUID.obtenerValorDefecto());
        setIdUsuario(UtilUUID.obtenerValorDefecto());
        setNombreUsuario(UtilTexto.getInstancia().obtenerValorDefecto());
        setBaseCaja(UtilMoneda.obtenerValorDefecto());
        setFechaApertura(UtilFecha.obtenerValorDefecto());
        setFechaCierre(null);
    }

    public SesionTrabajoDTO(final UUID codigo, final UUID idUsuario, final String nombreUsuario,
                            final BigDecimal baseCaja, final LocalDateTime fechaApertura, final LocalDateTime fechaCierre) {
        setCodigo(codigo);
        setIdUsuario(idUsuario);
        setNombreUsuario(nombreUsuario);
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

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final UUID idUsuario) {
        this.idUsuario = UtilUUID.obtenerValorDefecto(idUsuario);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreUsuario);
    }

    public BigDecimal getBaseCaja() {
        return baseCaja;
    }

    public void setBaseCaja(final BigDecimal baseCaja) {
        this.baseCaja = UtilMoneda.obtenerValorDefecto(baseCaja);
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(final LocalDateTime fechaApertura) {
        this.fechaApertura = UtilFecha.obtenerValorDefecto(fechaApertura);
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(final LocalDateTime fechaCierre) {
        this.fechaCierre = UtilFecha.obtenerValorDefecto(fechaCierre);
    }
}
