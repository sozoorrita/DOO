package co.edu.uco.FondaControl.crosscutting.utilitarios;

import java.time.LocalDateTime;

public final class UtilFecha {

    private static final LocalDateTime FECHA_DEFECTO = LocalDateTime.MIN;

    private UtilFecha() {
        // Constructor privado para evitar instanciación
    }

    public static LocalDateTime obtenerValorDefecto() {
        return FECHA_DEFECTO;
    }

    public static LocalDateTime obtenerValorDefecto(final LocalDateTime fecha) {
        return fecha != null ? fecha : FECHA_DEFECTO;
    }

    public static boolean esFechaValida(final LocalDateTime fecha) {
        return fecha != null && !fecha.isBefore(FECHA_DEFECTO);
    }

    public static LocalDateTime asegurarFechaValida(final LocalDateTime fecha) {
        return esFechaValida(fecha) ? fecha : FECHA_DEFECTO;
    }

    public static boolean esPosterior(final LocalDateTime fecha1, final LocalDateTime fecha2) {
        return fecha1 != null && fecha2 != null && fecha1.isAfter(fecha2);
    }

    public static boolean esAnterior(final LocalDateTime fecha1, final LocalDateTime fecha2) {
        return fecha1 != null && fecha2 != null && fecha1.isBefore(fecha2);
    }
}
