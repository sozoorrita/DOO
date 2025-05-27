package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class CategoriaDomain {
	private UUID codigo;
	private String nombre;

	CategoriaDomain() {
		setCodigo(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public CategoriaDomain(final UUID codigo, final String nombre) {
		setCodigo(codigo);
		setNombre(nombre);
	}

	public static CategoriaDomain obtenerValorDefecto() {
		return new CategoriaDomain();
	}

	static CategoriaDomain obtenerValorDefecto(final CategoriaDomain entidad) {
		return entidad != null ? entidad : obtenerValorDefecto();
	}

	public UUID getCodigo() {
		return codigo;
	}

	public void setCodigo(final UUID codigo) {
		this.codigo = UtilUUID.obtenerValorDefecto(codigo);
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
	}
}
