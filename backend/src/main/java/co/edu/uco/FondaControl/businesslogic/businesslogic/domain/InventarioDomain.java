package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class InventarioDomain {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private IndicadorInventarioDomain indicador;

    // Constructor por defecto
    public InventarioDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCantidad(0);
        setIndicador(IndicadorInventarioDomain.obtenerValorDefecto());
    }

    public InventarioDomain(final UUID codigo, final String nombreProducto, final int cantidad, final IndicadorInventarioDomain indicador) {
        setCodigo(codigo);
        setNombreProducto(nombreProducto);
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

    private void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    private void setNombreProducto(final String nombreProducto) {
        this.nombreProducto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreProducto);
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
        return indicador.getCodigo();
    }

    public String getNombreIndicador() {
        return indicador.getNombre();
    }

    private void setIndicador(final IndicadorInventarioDomain indicador) {
        this.indicador = UtilObjeto.getInstancia().obtenerValorDefecto(indicador, IndicadorInventarioDomain.obtenerValorDefecto());
    }

}
