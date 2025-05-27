package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Rol.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.RolDTO;

public final class RolDTOAssembler implements DTOAssembler<RolDTO, RolDomain> {

    private static final RolDTOAssembler INSTANCIA = new RolDTOAssembler();

    private RolDTOAssembler() {
        super();
    }

    public static RolDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public RolDomain toDomain(final RolDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return RolDomain.obtenerValorDefecto();
        }
        return RolDomain.crear(dto.getCodigo(), dto.getNombre());
    }

    @Override
    public RolDTO toDto(final RolDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return RolDTO.obtenerValorDefecto();
        }
        return RolDTO.builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
                .crear();
    }

    @Override
    public List<RolDomain> toDomainList(final List<RolDTO> dtoList) {
        final List<RolDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<RolDTO> toDtoList(final List<RolDomain> domainList) {
        final List<RolDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}