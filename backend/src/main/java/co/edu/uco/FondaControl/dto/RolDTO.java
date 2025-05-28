package co.edu.uco.FondaControl.dto;

import java.util.UUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class RolDTO {
    private UUID codigo;
    private String nombre;

    private RolDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    private RolDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
    }

    public static RolDTO obtenerValorDefecto() {
        return builder().crear();
    }

    public static RolDTO obtenerValorDefecto(final RolDTO entidad) {
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo = UtilUUID.obtenerValorDefecto();
        private String nombre = UtilTexto.getInstancia().obtenerValorDefecto();

        public Builder() {}

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public RolDTO crear() {
            return new RolDTO(this);
        }
    }
}
