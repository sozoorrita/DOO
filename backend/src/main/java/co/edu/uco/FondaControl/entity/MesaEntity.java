package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class MesaEntity {
    private UUID codigo;
    private String nombre;
    private UUID codigoEstadoMesa;

    public MesaEntity() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoEstadoMesa(UtilUUID.obtenerValorDefecto());
    }

    public MesaEntity(final UUID codigo, final String nombre, final UUID codigoEstadoMesa) {
        setCodigo(codigo);
        setNombre(nombre);
        setCodigoEstadoMesa(codigoEstadoMesa);
    }

    public static MesaEntity obtenerValorDefecto() {
        return new MesaEntity();
    }

    public static MesaEntity obtenerValorDefecto(final MesaEntity entidad) {
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
}
