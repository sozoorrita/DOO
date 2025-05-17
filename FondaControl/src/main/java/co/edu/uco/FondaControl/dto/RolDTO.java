package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class RolDTO {
    private UUID codigo;
    private String nombre;

    public RolDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public RolDTO(final UUID codigo, final String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
    }

    private RolDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
    }

    public static RolDTO obtenerValorDefecto() {
        return new RolDTO();
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
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
    }

    public static class Builder {
        private UUID codigo;
        private String nombre;

        public Builder codigo(UUID codigo) {
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

    public static Builder builder() {
        return new Builder();
    }
}
