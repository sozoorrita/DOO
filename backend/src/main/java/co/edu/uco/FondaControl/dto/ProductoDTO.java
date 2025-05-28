package co.edu.uco.FondaControl.dto;

import java.util.UUID;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public class ProductoDTO {

	public UUID codigo;
	public String nombre;
	public double precioLugar;
	public double precioLlevar;
	private SubcategoriaDTO codigoSubcategoria;
	public int limiteCantidad;

	public ProductoDTO() {
		setCodigoProducto(UtilUUID.obtenerValorDefecto());
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public ProductoDTO(final UUID codigoProducto) {
		setCodigoProducto(codigoProducto);
		setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
	}

	public ProductoDTO(final UUID codigoProducto, final String nombre) {
		setCodigoProducto(codigoProducto);
		setNombre(nombre);
	}

	public static ProductoDTO obtenerValorDefecto() {
		return new ProductoDTO();
	}

	public static ProductoDTO obtenerValorDefecto(final ProductoDTO pais) {
		return UtilObjeto.getInstancia().obtenerValorDefecto(pais, obtenerValorDefecto());
	}
	
	private ProductoDTO builder(final Builder builder) {
		setCodigoProducto(builder.codigoProducto);
		setNombre(builder.nombre);
		return this;
	}

	public UUID getCodigo() {
		return codigo;
	}

	public void setCodigoProducto(UUID codigoProducto) {
		this.codigo = codigoProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public SubcategoriaDTO getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	public void setCodigoSubcategoria(SubcategoriaDTO codigoSubcategoria) {
		this.codigoSubcategoria = codigoSubcategoria;
	}

	public int getLimiteCantidad() {
		return limiteCantidad;
	}

	public void setLimiteCantidad(int limiteCantidad) {
		this.limiteCantidad = limiteCantidad;
	}
	
	public static class Builder {

		private UUID codigoProducto;
		private String nombre;

		public Builder id(final UUID codigoProducto) {
			this.codigoProducto = codigoProducto;
			return this;
		}

		public Builder nombre(final String nombre) {
			this.nombre = nombre;
			return this;
		}

		public ProductoDTO crear() {
			return new ProductoDTO();
		}
	}

	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}

}
