package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Categoria.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.CategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.CategoriaDTO;

public final class CategoriaDTOAssembler implements DTOAssembler<CategoriaDTO, CategoriaDomain> {

	private static final CategoriaDTOAssembler INSTANCIA = new CategoriaDTOAssembler();

	private CategoriaDTOAssembler() {
		super();
	}

	public static CategoriaDTOAssembler getInstancia() {
		return INSTANCIA;
	}

	@Override
	public CategoriaDomain toDomain(final CategoriaDTO dto) {
		if (UtilObjeto.esNulo(dto)) {
			return CategoriaDomain.obtenerValorDefecto();
		}
		return new CategoriaDomain(dto.getCodigo(), dto.getNombre());
	}

	@Override
	public CategoriaDTO toDto(final CategoriaDomain domain) {
		if (UtilObjeto.esNulo(domain)) {
			return CategoriaDTO.obtenerValorDefecto();
		}
		return CategoriaDTO.builder().codigo(domain.getCodigo())
				.nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre())).crear();
	}

	@Override
	public List<CategoriaDomain> toDomainList(final List<CategoriaDTO> dtoList) {
		final List<CategoriaDomain> resultado = new ArrayList<>();
		for (final var dto : dtoList) {
			resultado.add(toDomain(dto));
		}
		return resultado;
	}

	@Override
	public List<CategoriaDTO> toDtoList(final List<CategoriaDomain> domainList) {
		final List<CategoriaDTO> resultado = new ArrayList<>();
		for (final var domain : domainList) {
			resultado.add(toDto(domain));
		}
		return resultado;
	}
}
