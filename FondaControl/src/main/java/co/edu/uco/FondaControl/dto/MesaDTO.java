package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class MesaDTO {
    private UUID codigo;
    private String nombre;
    private UUID codigoEstadoMesa;

    public MesaDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoEstadoMesa(UtilUUID.obtenerValorDefecto());
    }

    public MesaDTO(final UUID codigo, final String nombre, final UUID codigoEstadoMesa) {
        setCodigo(codigo);
        setNombre(nombre);
        setCodigoEstadoMesa(codigoEstadoMesa);
    }

    private MesaDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
        setCodigoEstadoMesa(builder.codigoEstadoMesa);
    }

    public static MesaDTO obtenerValorDefecto() {
        return new MesaDTO();
    }

    public static MesaDTO obtenerValorDefecto(final MesaDTO entidad) {
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

    public UUID getCodigoEstadoMesa() {
        return codigoEstadoMesa;
    }

    public void setCodigoEstadoMesa(final UUID codigoEstadoMesa) {
        this.codigoEstadoMesa = UtilUUID.obtenerValorDefecto(codigoEstadoMesa);
    }

    public static class Builder {
        private UUID codigo;
        private String nombre;
        private UUID codigoEstadoMesa;

        public Builder codigo(UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder codigoEstadoMesa(final UUID codigoEstadoMesa) {
            this.codigoEstadoMesa = codigoEstadoMesa;
            return this;
        }

        public MesaDTO crear() {
            return new MesaDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
