package co.edu.uco.FondaControl.entity;

import java.util.UUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class ProductoEntity {
    private UUID codigo;
    private String nombre;
    private double precioLugar;
    private double precioLlevar;
    private SubcategoriaEntity subcategoria;
    private int limiteCantidad;
    
    public ProductoEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setPrecioLugar(0.0);
        setPrecioLlevar(0.0);
        setSubcategoria(SubcategoriaEntity.obtenerValorDefecto());
        setLimiteCantidad(0);
    }

    private ProductoEntity(final Builder builder) {
        setCodigo(builder.codigo);
        setNombre(builder.nombre);
        setPrecioLugar(builder.precioLugar);
        setPrecioLlevar(builder.precioLlevar);
        setSubcategoria(builder.subcategoria);
        setLimiteCantidad(builder.limiteCantidad);
    }

    public static ProductoEntity obtenerValorDefecto() {
        return builder().crear();
    }

    public static ProductoEntity obtenerValorDefecto(final ProductoEntity entidad) {
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

    public double getPrecioLugar() {
        return precioLugar;
    }

    public void setPrecioLugar(final double precioLugar) {
        this.precioLugar = precioLugar;
    }

    public double getPrecioLlevar() {
        return precioLlevar;
    }

    public void setPrecioLlevar(final double precioLlevar) {
        this.precioLlevar = precioLlevar;
    }

    public SubcategoriaEntity getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(final SubcategoriaEntity subcategoria) {
        this.subcategoria = UtilObjeto.getInstancia().obtenerValorDefecto(subcategoria, SubcategoriaEntity.obtenerValorDefecto());
    }

    public int getLimiteCantidad() {
        return limiteCantidad;
    }

    public void setLimiteCantidad(final int limiteCantidad) {
        this.limiteCantidad = limiteCantidad;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo = UtilUUID.obtenerValorDefecto();
        private String nombre = UtilTexto.getInstancia().obtenerValorDefecto();
        private double precioLugar = 0.0;
        private double precioLlevar = 0.0;
        private SubcategoriaEntity subcategoria = SubcategoriaEntity.obtenerValorDefecto();
        private int limiteCantidad = 0;

        private Builder() {}

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(final String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder precioLugar(final double precioLugar) {
            this.precioLugar = precioLugar;
            return this;
        }

        public Builder precioLlevar(final double precioLlevar) {
            this.precioLlevar = precioLlevar;
            return this;
        }

        public Builder subcategoria(final SubcategoriaEntity subcategoria) {
            this.subcategoria = subcategoria;
            return this;
        }

        public Builder limiteCantidad(final int limiteCantidad) {
            this.limiteCantidad = limiteCantidad;
            return this;
        }

        public ProductoEntity crear() {
            return new ProductoEntity(this);
        }
    }
}