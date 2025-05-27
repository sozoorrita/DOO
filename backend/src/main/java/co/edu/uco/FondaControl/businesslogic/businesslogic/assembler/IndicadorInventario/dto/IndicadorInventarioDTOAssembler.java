package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario.dto;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.IndicadorInventarioDTO;

import java.util.ArrayList;
import java.util.List;

public final class IndicadorInventarioDTOAssembler implements DTOAssembler<IndicadorInventarioDTO, IndicadorInventarioDomain> {

    private static final IndicadorInventarioDTOAssembler INSTANCE = new IndicadorInventarioDTOAssembler();

    private IndicadorInventarioDTOAssembler() {
        super();
    }

    public static IndicadorInventarioDTOAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public IndicadorInventarioDTO toDto(final IndicadorInventarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return new IndicadorInventarioDTO(); // Usa constructor por defecto
        }

        return new IndicadorInventarioDTO(domain.getCodigo(), domain.getNombre());
    }

    @Override
    public IndicadorInventarioDomain toDomain(final IndicadorInventarioDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return IndicadorInventarioDomain.obtenerValorDefecto();
        }

        return new IndicadorInventarioDomain(dto.getCodigo(), UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombre()));
    }

    @Override
    public List<IndicadorInventarioDomain> toDomainList(final List<IndicadorInventarioDTO> dtoList) {
        final List<IndicadorInventarioDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<IndicadorInventarioDTO> toDtoList(final List<IndicadorInventarioDomain> domainList) {
        final List<IndicadorInventarioDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }

}
