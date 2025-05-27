package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.TipoVenta.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.TipoVentaDTO;

public final class TipoVentaDTOAssembler implements DTOAssembler<TipoVentaDTO, TipoVentaDomain> {

    private static final TipoVentaDTOAssembler INSTANCIA = new TipoVentaDTOAssembler();

    private TipoVentaDTOAssembler() {
        super();
    }

    public static TipoVentaDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public TipoVentaDomain toDomain(final TipoVentaDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return TipoVentaDomain.obtenerValorDefecto();
        }
        return TipoVentaDomain.crear(dto.getCodigo(), dto.getNombre());
    }

    @Override
    public TipoVentaDTO toDto(final TipoVentaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return TipoVentaDTO.obtenerValorDefecto();
        }
        return TipoVentaDTO.builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
                .crear();
    }

    @Override
    public List<TipoVentaDomain> toDomainList(final List<TipoVentaDTO> dtoList) {
        final List<TipoVentaDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<TipoVentaDTO> toDtoList(final List<TipoVentaDomain> domainList) {
        final List<TipoVentaDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}