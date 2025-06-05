package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.CategoriaEntity;

public final class CategoriaEntityAssembler implements EntityAssembler<CategoriaEntity, CategoriaDomain> {

	private static final CategoriaEntityAssembler INSTANCIA = new CategoriaEntityAssembler();

	private CategoriaEntityAssembler() {
		super();
	}

	public static CategoriaEntityAssembler getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CategoriaEntity toEntity(final CategoriaDomain domain) {
		if (UtilObjeto.esNulo(domain)) {
			return CategoriaEntity.obtenerValorDefecto();
		}
		return CategoriaEntity.builder()
				.codigo(domain.getCodigo())
				.nombre(domain.getNombre())
				.fechaCreacion(domain.getFechaCreacion())
				.fechaEliminacion(domain.getFechaEliminacion())
				.crear();
	}

	@Override
	public CategoriaDomain toDomain(final CategoriaEntity entity) {
		final var safe = CategoriaEntity.obtenerValorDefecto(entity);
		return new CategoriaDomain(
				safe.getCodigo(),
				safe.getNombre(),
				safe.getFechaCreacion(),
				safe.getFechaEliminacion()
		);
	}

	@Override
	public List<CategoriaDomain> toDomainList(final List<CategoriaEntity> entityList) {
		final List<CategoriaDomain> resultado = new ArrayList<>();
		for (final var entity : entityList) {
			resultado.add(toDomain(entity));
		}
		return resultado;
	}
}
