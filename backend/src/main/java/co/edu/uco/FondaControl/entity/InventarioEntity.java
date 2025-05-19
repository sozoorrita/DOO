package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.util.UUID;

public final class InventarioEntity {
    private UUID codigo;
    private String nombreProducto;
    private int cantidad;
    private co.edu.uco.FondaControl.entity.IndicadorInventarioEntity codigoIndicador;

    public InventarioEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoIndicador(new IndicadorInventarioEntity());
    }

    public InventarioEntity(final UUID id) {
        setCodigo(id);
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoIndicador(IndicadorInventarioEntity.obtenerValorDefecto());
    }

    public InventarioEntity(final UUID id, String nombreProducto, int cantidad, final IndicadorInventarioEntity codigoIndicador) {
        setCodigo(id);
        setNombreProducto(nombreProducto);
        setCantidad(cantidad);
        setCodigoIndicador(codigoIndicador);
    }

    public UUID getId() {
        return codigo;
    }

    public void setCodigo(final UUID id) {
        this.codigo = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(final String nombreProducto) {
        this.nombreProducto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombreProducto);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = Integer.parseInt(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(String.valueOf(cantidad)));
    }

    public IndicadorInventarioEntity getCodigoIndicador() {
        return codigoIndicador;
    }

    public void setCodigoIndicador(final IndicadorInventarioEntity codigoIndicador) {
        this.codigoIndicador = IndicadorInventarioEntity.obtenerValorDefecto(codigoIndicador);
    }



}