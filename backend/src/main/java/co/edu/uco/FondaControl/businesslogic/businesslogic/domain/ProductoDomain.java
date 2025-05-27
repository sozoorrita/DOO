package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class ProductoDomain {
	private UUID codigo;
	private String nombre;
	private double precioLugar;
	private double precioLlevar;
	private UUID codigoSubcategoria;
	private int limiteCantidad;

	ProductoDomain() {
		setCodigo(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
		setPrecioLugar(0.0);
		setPrecioLlevar(0.0);
		setCodigoSubcategoria(UtilUUID.obtenerValorDefecto());
		setLimiteCantidad(0);
	}

	public ProductoDomain(final UUID codigo, final String nombre, final double precioLugar, final double precioLlevar,
			final UUID codigoSubcategoria, final int limiteCantidad) {
		setCodigo(codigo);
		setNombre(nombre);
		setPrecioLugar(precioLugar);
		setPrecioLlevar(precioLlevar);
		setCodigoSubcategoria(codigoSubcategoria);
		setLimiteCantidad(limiteCantidad);
	}

	public static ProductoDomain obtenerValorDefecto() {
		return new ProductoDomain();
	}

	static ProductoDomain obtenerValorDefecto(final ProductoDomain entidad) {
		return entidad != null ? entidad : obtenerValorDefecto();
	}

	public UUID getCodigo() {
		return codigo;
	}

	private void setCodigo(final UUID codigo) {
		this.codigo = UtilUUID.obtenerValorDefecto(codigo);
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(final String nombre) {
		this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
	}

	public double getPrecioLugar() {
		return precioLugar;
	}

	private void setPrecioLugar(final double precioLugar) {
		this.precioLugar = precioLugar >= 0.0 ? precioLugar : 0.0;
	}

	public double getPrecioLlevar() {
		return precioLlevar;
	}

	private void setPrecioLlevar(final double precioLlevar) {
		this.precioLlevar = precioLlevar >= 0.0 ? precioLlevar : 0.0;
	}

	public UUID getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	private void setCodigoSubcategoria(final UUID codigoSubcategoria) {
		this.codigoSubcategoria = UtilUUID.obtenerValorDefecto(codigoSubcategoria);
	}

	public int getLimiteCantidad() {
		return limiteCantidad;
	}

	private void setLimiteCantidad(final int limiteCantidad) {
		this.limiteCantidad = limiteCantidad >= 0 ? limiteCantidad : 0;
	}
}
