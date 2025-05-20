package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;


import java.util.UUID;

public final class IndicadorInventarioDomain {
    private UUID codigo;
    private String nombre;

    IndicadorInventarioDomain() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());

    }

    public IndicadorInventarioDomain(final UUID codigo, final String nombre) {
        setCodigo(codigo);
        setNombre(nombre);
    }
    public static IndicadorInventarioDomain obtenerValorDefecto() {
        return new IndicadorInventarioDomain();
    }
    static UUID obtenerValorDefecto(final IndicadorInventarioDomain indicador) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(indicador, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    private void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(final String nombre) {
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
    }


}