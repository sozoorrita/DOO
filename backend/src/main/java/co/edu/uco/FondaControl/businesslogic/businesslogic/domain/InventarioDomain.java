package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class InventarioDomain {
    private UUID codigo;
    private ProductoDomain producto;
    private int cantidad;
    private IndicadorInventarioDomain indicador;


    public InventarioDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setProducto(null);
        setCantidad(0);
        setIndicador(IndicadorInventarioDomain.obtenerValorDefecto());
    }

    public InventarioDomain(final UUID codigo, final ProductoDomain producto, final int cantidad, final IndicadorInventarioDomain indicador) {
        setCodigo(codigo);
        setProducto(producto);
        setCantidad(cantidad);
        setIndicador(indicador);
    }

    public static InventarioDomain obtenerValorDefecto() {
        return new InventarioDomain();
    }

    public static UUID obtenerValorDefecto(final InventarioDomain inventario) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(inventario, obtenerValorDefecto()).getCodigo();
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public ProductoDomain getProducto() {
        return producto;
    }

    private void setProducto(final ProductoDomain producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    private void setCantidad(final int cantidad) {
        this.cantidad = Math.max(0, cantidad);
    }

    public IndicadorInventarioDomain getIndicador() {
        return indicador;
    }

    public UUID getCodigoIndicador() {
        return indicador != null ? indicador.getCodigo() : null;
    }

    public String getNombreIndicador() {
        return indicador != null ? indicador.getNombre() : "";
    }

    private void setIndicador(final IndicadorInventarioDomain indicador) {
        this.indicador = UtilObjeto.getInstancia().obtenerValorDefecto(indicador, IndicadorInventarioDomain.obtenerValorDefecto());
    }
}
