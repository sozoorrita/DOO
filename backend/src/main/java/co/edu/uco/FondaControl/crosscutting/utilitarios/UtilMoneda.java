package co.edu.uco.FondaControl.crosscutting.utilitarios;

import java.math.BigDecimal;

public final class UtilMoneda {

    private static final BigDecimal VALOR_DEFECTO = BigDecimal.ZERO;

    private UtilMoneda() {
        // Constructor privado para evitar instanciación
    }

    public static BigDecimal obtenerValorDefecto() {
        return VALOR_DEFECTO;
    }

    public static BigDecimal obtenerValorDefecto(final BigDecimal valor) {
        return (valor != null && esNoNegativo(valor)) ? valor : VALOR_DEFECTO;
    }

    public static BigDecimal redondear(final BigDecimal valor, final int decimales) {
        if (valor == null) {
            return VALOR_DEFECTO;
        }
        return valor.setScale(decimales, BigDecimal.ROUND_HALF_UP);
    }

    public static boolean esNoNegativo(final BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) >= 0;
    }

    public static BigDecimal asegurarNoNegativo(final BigDecimal valor) {
        return (valor != null && esNoNegativo(valor)) ? valor : VALOR_DEFECTO;
    }
}
