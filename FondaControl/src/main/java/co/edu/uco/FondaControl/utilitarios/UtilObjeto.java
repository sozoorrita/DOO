package co.edu.uco.FondaControl.utilitarios;

public final class UtilObjeto {
    private static UtilObjeto instancia;
    private UtilObjeto(){

    }
    public static synchronized UtilObjeto getInstancia(){

        if (instancia == null){
            instancia = new UtilObjeto();
        }
        return instancia;
    }
    public <o>boolean esNulo(final o objeto){
        return objeto == null;
    }

    public <o> o obtenerValorDefecto(final o valorOriginal, final o valorDefecto){

        return esNulo(valorOriginal) ? valorDefecto : valorOriginal;
    }

    }

