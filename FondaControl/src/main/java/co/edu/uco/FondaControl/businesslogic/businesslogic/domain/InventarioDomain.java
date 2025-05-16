package FondaControl.src.main.java.co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.util.UUID;

public final class InventarioDomain {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private IndicadorInventarioDomain codigoIndicador;

    InventarioDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCantidad(0);
        setCodigoIndicador(IndicadorInventarioDomain.obtenerValorDefecto());
    }

    public InventarioDomain(final UUID codigo, final String nombreProducto, final int cantidad, final IndicadorInventarioDomain codigoIndicador) {
        setCodigo(codigo);
        setNombreProducto(nombreProducto);
        setCantidad(cantidad);
        setCodigoIndicador(codigoIndicador);
    }

    static InventarioDomain obtenerValorDefecto() {
        return new InventarioDomain();
    }

    static InventarioDomain obtenerValorDefecto(final InventarioDomain inventario) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(inventario, obtenerValorDefecto());
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

    public IndicadorInventarioDomain getCodigoIndicador() {
        return codigoIndicador;
    }

    private void setCodigoIndicador(final IndicadorInventarioDomain codigoIndicador) {
        this.codigoIndicador = UtilObjeto.getInstancia().obtenerValorDefecto(codigoIndicador, IndicadorInventarioDomain.obtenerValorDefecto());
    }
}