package co.edu.uco.FondaControl.entity;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public class ProductoEntity {

	private UUID codigoProducto;
	private String nombreProducto;
	private double precioLugar;
	private double precioLlevar;
	private SubcategoriaEntity codigoSubcategoria;
	private int limiteCantidad;

	public ProductoEntity() {
		setCodigoProducto(UtilUUID.obtenerValorDefecto());
		UtilTexto.getInstancia();
		setNombre(UtilTexto.obtenerValorDefecto());
	}

	public ProductoEntity(final UUID codigoProducto) {
		setCodigoProducto(codigoProducto);
		UtilTexto.getInstancia();
		setNombre(UtilTexto.obtenerValorDefecto());
	}

	public ProductoEntity(final UUID codigoProducto, final String nombreProducto) {
		setCodigoProducto(codigoProducto);
		setNombre(nombreProducto);
	}

	public static ProductoEntity obtenerValorDefecto() {
		return new ProductoEntity();
	}

	public static ProductoEntity obtenerValorDefecto(final ProductoEntity pais) {
		return UtilObjeto.getInstancia().obtenerValorDefecto(pais, obtenerValorDefecto());
	}

	public UUID getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(UUID codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombre(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public double getPrecioLugar() {
		return precioLugar;
	}

	public void setPrecioLugar(double precioLugar) {
		this.precioLugar = precioLugar;
	}

	public double getPrecioLlevar() {
		return precioLlevar;
	}

	public void setPrecioLlevar(double precioLlevar) {
		this.precioLlevar = precioLlevar;
	}

	public SubcategoriaEntity getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	public void setCodigoSubcategoria(SubcategoriaEntity codigoSubcategoria) {
		this.codigoSubcategoria = codigoSubcategoria;
	}

	public int getLimiteCantidad() {
		return limiteCantidad;
	}

	public void setLimiteCantidad(int limiteCantidad) {
		this.limiteCantidad = limiteCantidad;
	}
}
