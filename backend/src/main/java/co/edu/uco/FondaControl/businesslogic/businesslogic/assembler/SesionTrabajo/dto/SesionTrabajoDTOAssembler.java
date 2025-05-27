package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.SesionTrabajo.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.dto.SesionTrabajoDTO;

public final class SesionTrabajoDTOAssembler implements DTOAssembler<SesionTrabajoDTO, SesionTrabajoDomain> {

    private static final SesionTrabajoDTOAssembler INSTANCIA = new SesionTrabajoDTOAssembler();

    private SesionTrabajoDTOAssembler() {
        super();
    }

    public static SesionTrabajoDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public SesionTrabajoDomain toDomain(final SesionTrabajoDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return SesionTrabajoDomain.obtenerValorDefecto();
        }

        final var usuario = new UsuarioDomain(
                dto.getIdUsuario(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombreUsuario()),
                UtilUUID.obtenerValorDefecto(),
                ""
        );

        return SesionTrabajoDomain.crear(
                dto.getCodigo(),
                usuario,
                UtilMoneda.obtenerValorDefecto(dto.getBaseCaja()),
                UtilFecha.obtenerValorDefecto(dto.getFechaApertura()),
                UtilFecha.obtenerValorDefecto(dto.getFechaCierre())
        );
    }

    @Override
    public SesionTrabajoDTO toDto(final SesionTrabajoDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return new SesionTrabajoDTO();
        }

        return new SesionTrabajoDTO(
                domain.getCodigo(),
                domain.getUsuario().getId(),
                domain.getUsuario().getNombre(),
                domain.getBaseCaja(),
                domain.getFechaApertura(),
                domain.getFechaCierre()
        );
    }

    @Override
    public List<SesionTrabajoDomain> toDomainList(final List<SesionTrabajoDTO> dtoList) {
        final List<SesionTrabajoDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<SesionTrabajoDTO> toDtoList(final List<SesionTrabajoDomain> domainList) {
        final List<SesionTrabajoDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
