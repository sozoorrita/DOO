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
		setId(id);
		setNombre(nombre);
		setCodigoRol(codigoRol);
		setContrasena(contrasena);
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

	public void setId(final UUID id) {
		this.id = UtilUUID.obtenerValorDefecto(id);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
	}

	public UUID getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(final UUID codigoRol) {
		this.codigoRol = UtilUUID.obtenerValorDefecto(codigoRol);
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(final String contrasena) {
		this.contrasena = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(contrasena);
	}
}
