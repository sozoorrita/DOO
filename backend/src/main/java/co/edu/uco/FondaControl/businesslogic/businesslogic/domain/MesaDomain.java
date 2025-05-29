package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.util.UUID;

public final class MesaDomain {

    private final UUID codigo;
    private final String identificador;
    private final EstadoMesaDomain estado;

    public MesaDomain(final UUID codigo, final String identificador, final EstadoMesaDomain estado) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
        this.identificador = validarIdentificador(identificador);
        this.estado = UtilObjeto.getInstancia().obtenerValorDefecto(estado, EstadoMesaDomain.obtenerValorDefecto());
    }

    public static MesaDomain crear(final UUID codigo, final String identificador, final EstadoMesaDomain estado) {
        return new MesaDomain(codigo, identificador, estado);
    }

    public static MesaDomain obtenerValorDefecto() {
        return new MesaDomain(UtilUUID.obtenerValorDefecto(), "", EstadoMesaDomain.obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public EstadoMesaDomain getEstado() {
        return estado;
    }

    private static String validarIdentificador(final String identificador) {
        var texto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(identificador);
        if (UtilTexto.getInstancia().esNula(texto)) {
            throw new IllegalArgumentException("El identificador de la mesa no puede ser nulo ni vacío.");
        }
        if (texto.length() > 10) {
            throw new IllegalArgumentException("El identificador de la mesa no puede exceder los 10 caracteres.");
        }
        return texto;
    }
}
