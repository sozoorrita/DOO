package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class IndicadorInventarioDomain {
	private final UUID codigo;
	private final String nombre;

	public IndicadorInventarioDomain(final UUID codigo, final String nombre) {
		this.codigo = UtilUUID.obtenerValorDefecto(codigo);
		this.nombre = validarNombre(nombre);
	}

	public static IndicadorInventarioDomain obtenerValorDefecto() {
		return new IndicadorInventarioDomain(UtilUUID.obtenerValorDefecto(), "");
	}

	public static UUID obtenerCodigoDefecto(final IndicadorInventarioDomain indicador) {
		return UtilObjeto.getInstancia().obtenerValorDefecto(indicador, obtenerValorDefecto()).getCodigo();
	}

	public UUID getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
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
