package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class FormaPagoDTO {
    private UUID codigo;
    private String nombre;

    public FormaPagoDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public FormaPagoDTO(final UUID codigo, final String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
    }

    private FormaPagoDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
    }

    public static FormaPagoDTO obtenerValorDefecto() {
        return new FormaPagoDTO();
    }

    public static FormaPagoDTO obtenerValorDefecto(final FormaPagoDTO entidad) {
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

        public FormaPagoDTO crear() {
            return new FormaPagoDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
