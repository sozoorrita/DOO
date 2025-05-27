package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.SubcategoriaDTO;

public final class SubcategoriaDTOAssembler implements DTOAssembler<SubcategoriaDTO, SubcategoriaDomain> {

    private static final SubcategoriaDTOAssembler INSTANCIA = new SubcategoriaDTOAssembler();

    private SubcategoriaDTOAssembler() {
        super();
    }

    public static SubcategoriaDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public SubcategoriaDomain toDomain(final SubcategoriaDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return SubcategoriaDomain.obtenerValorDefecto();
        }
        return new SubcategoriaDomain(dto.getCodigo(), dto.getNombre(), dto.getCodigoCategoria());
    }

    @Override
    public SubcategoriaDTO toDto(final SubcategoriaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return SubcategoriaDTO.obtenerValorDefecto();
        }
        return SubcategoriaDTO.builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
                .codigoCategoria(domain.getCodigoCategoria())
                .crear();
    }

    @Override
    public List<SubcategoriaDomain> toDomainList(final List<SubcategoriaDTO> dtoList) {
        final List<SubcategoriaDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<SubcategoriaDTO> toDtoList(final List<SubcategoriaDomain> domainList) {
        final List<SubcategoriaDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
