package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class UsuarioDomain {
	private UUID id;
	private String nombre;
	private UUID codigoRol;
	private String contrasena;

	public UsuarioDomain() {
		setId(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
		setCodigoRol(UtilUUID.obtenerValorDefecto());
		setContrasena(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public UsuarioDomain(UUID id, String nombre, UUID codigoRol, String contrasena) {
		this.id = UtilUUID.obtenerValorDefecto(id);
		this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
		this.codigoRol = UtilUUID.obtenerValorDefecto(codigoRol);
		this.contrasena = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(contrasena);
	}

	public static UsuarioDomain obtenerValorDefecto() {
		return new UsuarioDomain();
	}

	static UsuarioDomain obtenerValorDefecto(final UsuarioDomain usuario) {
		return (usuario != null) ? usuario : obtenerValorDefecto();
	}

	public UUID getId() {
		return id;
	}

	private void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
	}

	public UUID getCodigoRol() {
		return codigoRol;
	}

	private void setCodigoRol(final UUID codigoRol) {
		this.codigoRol = UtilUUID.obtenerValorDefecto(codigoRol);
	}

	public String getContrasena() {
		return contrasena;
	}

	private void setContrasena(final String contrasena) {
		this.contrasena = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(contrasena);
	}
}