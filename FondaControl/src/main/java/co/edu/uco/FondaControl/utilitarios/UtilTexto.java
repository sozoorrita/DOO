package co.edu.uco.FondaControl.utilitarios;

public final class UtilTexto {

    // Instancia única de la clase
    private static final UtilTexto INSTANCIA = new UtilTexto();

    // Constante para representar un valor vacío
    public static final String VACIO = "";

    // Constructor privado para evitar instanciacion externa
    private UtilTexto() {
    }

    // Método para obtener la instancia única
    public static UtilTexto getInstancia() {
        return INSTANCIA;
    }

    // Verifica si un valor es nulo
    public boolean esNula(final String valor) {
        return UtilObjeto.getInstancia().esNulo(valor);
    }

    // Devuelve un valor por defecto si el original es nulo o vacío
    public String obtenerValorDefecto(final String valorOriginal, final String valorDefecto) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(valorOriginal, valorDefecto);
    }

    // Sobrecarga para usar un valor vacío como valor por defecto
    public String obtenerValorDefecto(final String valor) {
        return obtenerValorDefecto(valor, VACIO);
    }
    public String obtenerValorDefecto() {
        return  VACIO;
    }

    // Quita espacios en blanco al inicio y al final de un valor
    public String quitarEspaciosBlancoInicioFin(final String valor) {
        return obtenerValorDefecto(valor).trim();
    }



    }

