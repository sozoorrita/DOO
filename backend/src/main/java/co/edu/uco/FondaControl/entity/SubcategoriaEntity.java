package co.edu.uco.FondaControl.entity;

import java.util.UUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class SubcategoriaEntity {
    private UUID codigo;
    private String nombre;
    private UUID codigoCategoria;

    private SubcategoriaEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoCategoria(UtilUUID.obtenerValorDefecto());
    }

    private SubcategoriaEntity(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
        setCodigoCategoria(builder.codigoCategoria);
    }

    public static SubcategoriaEntity obtenerValorDefecto() {
        return builder().crear();
    }

    public static SubcategoriaEntity obtenerValorDefecto(final SubcategoriaEntity entidad) {
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

    public UUID getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(final UUID codigoCategoria) {
        this.codigoCategoria = UtilUUID.obtenerValorDefecto(codigoCategoria);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo = UtilUUID.obtenerValorDefecto();
        private String nombre = UtilTexto.getInstancia().obtenerValorDefecto();
        private UUID codigoCategoria = UtilUUID.obtenerValorDefecto();

        private Builder() {}

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder codigoCategoria(final UUID codigoCategoria) {
            this.codigoCategoria = codigoCategoria;
            return this;
        }

        public SubcategoriaEntity crear() {
            return new SubcategoriaEntity(this);
        }
    }
}