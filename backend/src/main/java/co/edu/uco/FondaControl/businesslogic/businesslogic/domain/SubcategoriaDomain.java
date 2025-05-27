package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class SubcategoriaDomain {
	private UUID codigo;
	private String nombre;
	private UUID codigoCategoria;

	SubcategoriaDomain() {
		setCodigo(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
		setCodigoCategoria(UtilUUID.obtenerValorDefecto());
	}

	public SubcategoriaDomain(final UUID codigo, final String nombre, final UUID codigoCategoria) {
		setCodigo(codigo);
		setNombre(nombre);
		setCodigoCategoria(codigoCategoria);
	}

	public static SubcategoriaDomain obtenerValorDefecto() {
		return new SubcategoriaDomain();
	}

	static SubcategoriaDomain obtenerValorDefecto(final SubcategoriaDomain entidad) {
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

	public UUID getCodigoCategoria() {
		return codigoCategoria;
	}

	private void setCodigoCategoria(final UUID codigoCategoria) {
		this.codigoCategoria = UtilUUID.obtenerValorDefecto(codigoCategoria);
	}
}
