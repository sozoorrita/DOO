package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;

public final class EstadoMesaDTOAssembler implements DTOAssembler<EstadoMesaDTO, EstadoMesaDomain> {

    private static final EstadoMesaDTOAssembler INSTANCIA = new EstadoMesaDTOAssembler();

    private EstadoMesaDTOAssembler() {
        super();
    }

    public static EstadoMesaDTOAssembler getInstance() {
        return INSTANCIA;
    }

    @Override
    public EstadoMesaDomain toDomain(final EstadoMesaDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return EstadoMesaDomain.obtenerValorDefecto();
        }

        return EstadoMesaDomain.crear(dto.getCodigo(), dto.getNombre());
    }

    @Override
    public EstadoMesaDTO toDto(final EstadoMesaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return EstadoMesaDTO.obtenerValorDefecto();
        }

        return EstadoMesaDTO.builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
                .crear();
    }

    @Override
    public List<EstadoMesaDomain> toDomainList(final List<EstadoMesaDTO> dtoList) {
        final List<EstadoMesaDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<EstadoMesaDTO> toDtoList(final List<EstadoMesaDomain> domainList) {
        final List<EstadoMesaDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }

}
