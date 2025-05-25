package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class SesionTrabajoDomain {

    private final UUID codigo;
    private final UsuarioDomain usuario;
    private final BigDecimal baseCaja;
    private final LocalDateTime fechaApertura;
    private final LocalDateTime fechaCierre;

    public SesionTrabajoDomain(final UUID codigo, final UsuarioDomain usuario, final BigDecimal baseCaja,
                               final LocalDateTime fechaApertura, final LocalDateTime fechaCierre) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
        this.usuario = validarUsuario(usuario);
        this.baseCaja = UtilMoneda.asegurarNoNegativo(baseCaja);
        this.fechaApertura = UtilFecha.asegurarFechaValida(fechaApertura);
        this.fechaCierre = fechaCierre != null ? UtilFecha.asegurarFechaValida(fechaCierre) : null;

        if (this.fechaCierre != null && this.fechaCierre.isBefore(this.fechaApertura)) {
            throw new IllegalArgumentException("La fecha de cierre no puede ser anterior a la fecha de apertura.");
        }
    }

    public static SesionTrabajoDomain crear(final UUID codigo, final UsuarioDomain usuario, final BigDecimal baseCaja,
                                            final LocalDateTime fechaApertura, final LocalDateTime fechaCierre) {
        return new SesionTrabajoDomain(codigo, usuario, baseCaja, fechaApertura, fechaCierre);
    }

    public static SesionTrabajoDomain crearParaRegistro(final UsuarioDomain usuario, final BigDecimal baseCaja,
                                                        final LocalDateTime fechaApertura) {
        return new SesionTrabajoDomain(UtilUUID.generarNuevoUUID(), usuario, baseCaja, fechaApertura, null);
    }

    public static SesionTrabajoDomain obtenerValorDefecto() {
        return new SesionTrabajoDomain(UtilUUID.obtenerValorDefecto(),
                UsuarioDomain.obtenerValorDefecto(),
                UtilMoneda.obtenerValorDefecto(),
                UtilFecha.obtenerValorDefecto(),
                null);
    }

    public UUID getCodigo() {
        return codigo;
    }

    public UsuarioDomain getUsuario() {
        return usuario;
    }

    public BigDecimal getBaseCaja() {
        return baseCaja;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    private static UsuarioDomain validarUsuario(UsuarioDomain usuario) {
        if (UtilObjeto.esNulo(usuario)) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        return usuario;
    }
}
