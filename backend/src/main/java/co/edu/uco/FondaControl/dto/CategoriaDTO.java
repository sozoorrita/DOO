package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public class CategoriaDTO {

	private UUID codigoCategoria;
	public String nombre;

	public CategoriaDTO() {
		setCodigoCategoria(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public CategoriaDTO(final UUID codigoCategoria) {
		setCodigoCategoria(codigoCategoria);
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public CategoriaDTO(final UUID codigoCategoria, final String nombre) {
		setCodigoCategoria(codigoCategoria);
		setNombre(nombre);
	}

	public UUID getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(UUID codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
