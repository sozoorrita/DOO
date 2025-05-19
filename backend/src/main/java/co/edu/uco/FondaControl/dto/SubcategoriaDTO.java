package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public class SubcategoriaDTO {

	private UUID codigoSubcategoria;
	public String nombre;
	private UUID codigoCategoria;

	public SubcategoriaDTO() {
		setCodigoSubcategoria(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public SubcategoriaDTO(final UUID codigoSubcategoria) {
		setCodigoSubcategoria(codigoSubcategoria);
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public SubcategoriaDTO(final UUID codigoSubcategoria, final String nombre) {
		setCodigoSubcategoria(codigoSubcategoria);
		setNombre(nombre);
	}

	public UUID getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	public void setCodigoSubcategoria(UUID codigoSubcategoria) {
		this.codigoSubcategoria = codigoSubcategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public UUID getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(UUID codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
}
