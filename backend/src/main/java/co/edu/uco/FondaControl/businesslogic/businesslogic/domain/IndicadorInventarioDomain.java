package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class IndicadorInventarioDomain {
    private UUID codigo;
    private String nombre;

    public IndicadorInventarioDomain() {
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

    public static UUID obtenerCodigoDefecto(final IndicadorInventarioDomain indicador) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(indicador, obtenerValorDefecto()).getCodigo();
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
    private static String validarNombre(final String nombre) {
        final var texto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
        if (UtilTexto.getInstancia().esNula(texto)) {
            throw new IllegalArgumentException("El nombre del indicador no puede ser nulo ni vacío.");
        }
        if (texto.length() > 50) {
            throw new IllegalArgumentException("El nombre del indicador no puede exceder los 50 caracteres.");
        }
        return texto;
    }

}
