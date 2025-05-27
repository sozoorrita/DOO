package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.dto.EstadoMesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.MesaDTO;
import co.edu.uco.FondaControl.dto.EstadoMesaDTO;

public final class MesaDTOAssembler implements DTOAssembler<MesaDTO, MesaDomain> {

    private static final MesaDTOAssembler INSTANCIA = new MesaDTOAssembler();

    private MesaDTOAssembler() {
        super();
    }

    public static MesaDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public MesaDomain toDomain(final MesaDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return MesaDomain.obtenerValorDefecto();
        }

        final EstadoMesaDTO estadoDTO = new EstadoMesaDTO(dto.getCodigoEstadoMesa(), "");
        final EstadoMesaDomain estadoDomain = EstadoMesaDTOAssembler.getInstancia().toDomain(estadoDTO);

        return MesaDomain.crear(dto.getCodigo(), dto.getNombre(), estadoDomain);
    }

    @Override
    public MesaDTO toDto(final MesaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return MesaDTO.obtenerValorDefecto();
        }

        return MesaDTO.builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getIdentificador()))
                .codigoEstadoMesa(domain.getEstado().getCodigo())
                .crear();
    }

    @Override
    public List<MesaDomain> toDomainList(final List<MesaDTO> dtoList) {
        final List<MesaDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<MesaDTO> toDtoList(final List<MesaDomain> domainList) {
        final List<MesaDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
