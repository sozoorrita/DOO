package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class IndicadorInventarioDTO {

    private UUID codigo;
    private String nombre;

    public static IndicadorInventarioDTO obtenerValorDefecto() {
        return new IndicadorInventarioDTO();
    }


    public IndicadorInventarioDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public IndicadorInventarioDTO(final UUID codigo) {
        setCodigo(codigo);
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public IndicadorInventarioDTO(final UUID codigo, final String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
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
}
