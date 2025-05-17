package co.edu.uco.FondaControl.entity;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public class CategoriaEntity {

	private UUID codigoCategoria;
	private String nombre;

	public CategoriaEntity() {
		setCodigoCategoria(UtilUUID.obtenerValorDefecto());
		UtilTexto.getInstancia();
		setNombre(UtilTexto.obtenerValorDefecto());
	}

	public CategoriaEntity(final UUID codigoCategoria) {
		setCodigoCategoria(codigoCategoria);
		UtilTexto.getInstancia();
		setNombre(UtilTexto.obtenerValorDefecto());
	}

	public CategoriaEntity(final UUID codigoCategoria, final String nombre) {
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
