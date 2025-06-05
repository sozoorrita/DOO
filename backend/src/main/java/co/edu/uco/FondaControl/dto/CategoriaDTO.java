package co.edu.uco.FondaControl.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class CategoriaDTO {
    private UUID codigo;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEliminacion;

    public CategoriaDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setFechaCreacion(null);        // si es null, el setter asigna LocalDateTime.now()
        setFechaEliminacion(null);     // aún no eliminado
    }

    private CategoriaDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
        setFechaCreacion(builder.fechaCreacion);
        setFechaEliminacion(builder.fechaEliminacion);
    }

    public static CategoriaDTO obtenerValorDefecto() {
        return builder().crear();
    }

    public static CategoriaDTO obtenerValorDefecto(final CategoriaDTO entidad) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(entidad, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        String valor = UtilTexto.getInstancia().obtenerValorDefecto(nombre);
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(valor);
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final LocalDateTime fechaCreacion) {
        this.fechaCreacion = (fechaCreacion == null ? LocalDateTime.now() : fechaCreacion);
    }

    public LocalDateTime getFechaEliminacion() {
        return fechaEliminacion;
    }

    public void setFechaEliminacion(final LocalDateTime fechaEliminacion) {
        this.fechaEliminacion = fechaEliminacion;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo;
        private String nombre;
        private LocalDateTime fechaCreacion;
        private LocalDateTime fechaEliminacion;

        private Builder() {
            this.codigo = UtilUUID.obtenerValorDefecto();
            this.nombre = UtilTexto.getInstancia().obtenerValorDefecto();
            this.fechaCreacion = LocalDateTime.now();
            this.fechaEliminacion = null;
        }

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder fechaCreacion(final LocalDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return this;
        }

        public Builder fechaEliminacion(final LocalDateTime fechaEliminacion) {
            this.fechaEliminacion = fechaEliminacion;
            return this;
        }

        public CategoriaDTO crear() {
            return new CategoriaDTO(this);
        }
    }
}
