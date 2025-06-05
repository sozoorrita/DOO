package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

public final class CategoriaDomain {
	private UUID codigo;
	private String nombre;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaEliminacion;

	public CategoriaDomain() {
		setCodigo(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
		setFechaCreacion(LocalDateTime.now());
		setFechaEliminacion(null);
	}

	public CategoriaDomain(final UUID codigo, final String nombre) {
		setCodigo(codigo);
		setNombre(nombre);
		setFechaCreacion(LocalDateTime.now());
		setFechaEliminacion(null);
	}

	public CategoriaDomain(final UUID codigo, final String nombre, final LocalDateTime fechaCreacion,
						   final LocalDateTime fechaEliminacion) {
		setCodigo(codigo);
		setNombre(nombre);
		setFechaCreacion(fechaCreacion);
		setFechaEliminacion(fechaEliminacion);
	}

	public static CategoriaDomain obtenerValorDefecto() {
		return new CategoriaDomain();
	}

	static CategoriaDomain obtenerValorDefecto(final CategoriaDomain entidad) {
		return UtilObjeto.getInstancia().obtenerValorDefecto(entidad, obtenerValorDefecto());
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

	public void setNombre(final String nombre) {
		this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(final LocalDateTime fechaCreacion) {
		this.fechaCreacion = (fechaCreacion != null) ? fechaCreacion : LocalDateTime.now();
	}

	public LocalDateTime getFechaEliminacion() {
		return fechaEliminacion;
	}

	public void setFechaEliminacion(final LocalDateTime fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}
}
