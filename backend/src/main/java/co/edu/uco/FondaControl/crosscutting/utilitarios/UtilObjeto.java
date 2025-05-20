package co.edu.uco.FondaControl.crosscutting.utilitarios;

import java.util.UUID;

public final class UtilObjeto {
    private static UtilObjeto instancia;
    private UtilObjeto (){

    }
    public static synchronized UtilObjeto getInstancia(){

        if (instancia == null){
            instancia = new UtilObjeto();
        }
        return instancia;
    }
    public static <o>boolean esNulo(final o objeto){
        return objeto == null;
    }

    public <o> UUID obtenerValorDefecto(final o valorOriginal, final o valorDefecto){

        return (UUID) (esNulo(valorOriginal) ? valorDefecto : valorOriginal);
    }

}


