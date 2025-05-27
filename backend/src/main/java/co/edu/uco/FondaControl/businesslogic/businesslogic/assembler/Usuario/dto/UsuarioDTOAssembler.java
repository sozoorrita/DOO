package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Usuario.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.UsuarioDTO;

public final class UsuarioDTOAssembler implements DTOAssembler<UsuarioDTO, UsuarioDomain> {

    private static final UsuarioDTOAssembler INSTANCIA = new UsuarioDTOAssembler();

    private UsuarioDTOAssembler() {
        super();
    }

    public static UsuarioDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public UsuarioDomain toDomain(final UsuarioDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return UsuarioDomain.obtenerValorDefecto();
        }

        return new UsuarioDomain(
                dto.getId(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getNombre()),
                dto.getCodigoRol(),
                UtilTexto.getInstancia().obtenerValorDefecto(dto.getContrasena())
        );
    }

    @Override
    public UsuarioDTO toDto(final UsuarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return UsuarioDTO.obtenerValorDefecto();
        }

        return UsuarioDTO.builder()
                .id(domain.getId())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
                .codigoRol(domain.getCodigoRol())
                .contrasena(UtilTexto.getInstancia().obtenerValorDefecto(domain.getContrasena()))
                .crear();
    }

    @Override
    public List<UsuarioDomain> toDomainList(final List<UsuarioDTO> dtoList) {
        final List<UsuarioDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<UsuarioDTO> toDtoList(final List<UsuarioDomain> domainList) {
        final List<UsuarioDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
