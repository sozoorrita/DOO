package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.ProductoEntity;

public final class ProductoEntityAssembler implements EntityAssembler<ProductoEntity, ProductoDomain> {

	private static final ProductoEntityAssembler INSTANCIA = new ProductoEntityAssembler();

	private ProductoEntityAssembler() {
		super();
	}

	public static ProductoEntityAssembler getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ProductoEntity toEntity(final ProductoDomain domain) {
		if (UtilObjeto.esNulo(domain)) {
			return ProductoEntity.obtenerValorDefecto();
		}
		var entity = new ProductoEntity();
		entity.setCodigoProducto(domain.getCodigo());
		entity.setNombre(domain.getNombre());
		entity.setPrecioLugar(domain.getPrecioLugar());
		entity.setPrecioLlevar(domain.getPrecioLlevar());
		entity.setCodigoSubcategoria(null);
		entity.setLimiteCantidad(domain.getLimiteCantidad());
		return entity;
	}

	@Override
	public ProductoDomain toDomain(final ProductoEntity entity) {
		var safe = ProductoEntity.obtenerValorDefecto(entity);
		return new ProductoDomain(safe.getCodigoProducto(), safe.getNombreProducto(), safe.getPrecioLugar(),
				safe.getPrecioLlevar(),
				safe.getCodigoSubcategoria() != null ? safe.getCodigoSubcategoria().getCodigoSubcategoria() : null,
				safe.getLimiteCantidad());
	}

	@Override
	public List<ProductoDomain> toDomainList(final List<ProductoEntity> entityList) {
		final List<ProductoDomain> resultado = new ArrayList<>();
		for (ProductoEntity entity : entityList) {
			resultado.add(toDomain(entity));
		}
		return resultado;
	}
}
