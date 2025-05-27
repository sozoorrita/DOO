package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.DetalleVentaEntity;

public final class DetalleVentaEntityAssembler implements EntityAssembler<DetalleVentaEntity, DetalleVentaDomain> {

	private static final DetalleVentaEntityAssembler INSTANCIA = new DetalleVentaEntityAssembler();

	private DetalleVentaEntityAssembler() {
		super();
	}

	public static DetalleVentaEntityAssembler getInstancia() {
		return INSTANCIA;
	}

	@Override
	public DetalleVentaEntity toEntity(final DetalleVentaDomain domain) {
		if (UtilObjeto.esNulo(domain)) {
			return DetalleVentaEntity.obtenerValorDefecto();
		}
		var entity = new DetalleVentaEntity(domain.getCodigoDetalleVenta());
		entity.setNombreProducto(domain.getNombreProducto());
		entity.setPrecioAplicado(domain.getPrecioAplicado());
		entity.setCantidad(domain.getCantidad());
		return entity;
	}

	@Override
	public DetalleVentaDomain toDomain(final DetalleVentaEntity entity) {
		var safe = DetalleVentaEntity.obtenerValorDefecto(entity);
		return new DetalleVentaDomain(safe.getCodigoDetalleVenta(), safe.getNombreProducto(), safe.getPrecioAplicado(),
				safe.getCantidad());
	}

	@Override
	public List<DetalleVentaDomain> toDomainList(final List<DetalleVentaEntity> entityList) {
		final List<DetalleVentaDomain> resultado = new ArrayList<>();
		for (DetalleVentaEntity entity : entityList) {
			resultado.add(toDomain(entity));
		}
		return resultado;
	}
}
