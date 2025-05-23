package co.edu.uco.FondaControl.crosscutting.utilitarios;

import java.util.UUID;

public final class UtilObjeto {
    private static UtilObjeto instancia;

    private UtilObjeto() {}

    public static synchronized UtilObjeto getInstancia() {
        if (instancia == null) {
            instancia = new UtilObjeto();
        }
        return instancia;
    }

    public static <T> boolean esNulo(final T objeto) {
        return objeto == null;
    }

    public <T> T obtenerValorDefecto(final T valorOriginal, final T valorDefecto) {
        return esNulo(valorOriginal) ? valorDefecto : valorOriginal;
    }
}



