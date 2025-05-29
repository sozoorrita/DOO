package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class ProductoDomain {
    private final java.util.UUID codigo;
    private final String nombre;
    private final double precioLugar;
    private final double precioLlevar;
    private final SubcategoriaDomain subcategoria;
    private final int limiteCantidad;

    public ProductoDomain() {
        this(
            UtilUUID.obtenerValorDefecto(),
            UtilTexto.getInstancia().obtenerValorDefecto(),
            0.0,
            0.0,
            SubcategoriaDomain.obtenerValorDefecto(),
            0
        );
    }

    public ProductoDomain(final java.util.UUID codigo, final String nombre, final double precioLugar, final double precioLlevar,
                         final SubcategoriaDomain subcategoria, final int limiteCantidad) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
        this.precioLugar = precioLugar >= 0.0 ? precioLugar : 0.0;
        this.precioLlevar = precioLlevar >= 0.0 ? precioLlevar : 0.0;
        this.subcategoria = subcategoria != null ? subcategoria : SubcategoriaDomain.obtenerValorDefecto();
        this.limiteCantidad = Math.max(0, limiteCantidad);
    }

    public static ProductoDomain obtenerValorDefecto() {
        return new ProductoDomain();
    }

    public static ProductoDomain obtenerValorDefecto(final ProductoDomain entidad) {
        return entidad != null ? entidad : obtenerValorDefecto();
    }

    public java.util.UUID getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioLugar() {
        return precioLugar;
    }

    public double getPrecioLlevar() {
        return precioLlevar;
    }

    public SubcategoriaDomain getSubcategoria() {
        return subcategoria;
    }

    public int getLimiteCantidad() {
        return limiteCantidad;
    }
}
