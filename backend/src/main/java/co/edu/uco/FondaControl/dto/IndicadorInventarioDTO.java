package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class IndicadorInventarioDTO {

    private UUID codigo;
    private String nombre;

    public IndicadorInventarioDTO() {
        // El código será asignado por la base de datos
        this.codigo = null;
        this.nombre = UtilTexto.getInstancia().obtenerValorDefecto();
    }

    public IndicadorInventarioDTO(final UUID codigo, final String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
    }

    private IndicadorInventarioDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
    }

    public static IndicadorInventarioDTO obtenerValorDefecto() {
        return new IndicadorInventarioDTO();
    }

    public static IndicadorInventarioDTO obtenerValorDefecto(final IndicadorInventarioDTO indicador) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(indicador, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        // Permitimos null para que se genere en la base de datos
        this.codigo = UtilUUID.obtenerValorDefecto(codigo, null);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        if (nombre == null || nombre.isBlank()) {
            this.nombre = UtilTexto.getInstancia().obtenerValorDefecto();
        } else {
            this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
        }
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

        public IndicadorInventarioDTO crear() {
            return new IndicadorInventarioDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
