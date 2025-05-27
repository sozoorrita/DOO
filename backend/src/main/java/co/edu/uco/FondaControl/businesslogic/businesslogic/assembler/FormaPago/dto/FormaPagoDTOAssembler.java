package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.FormaPago.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.FormaPagoDTO;

public final class FormaPagoDTOAssembler implements DTOAssembler<FormaPagoDTO, FormaPagoDomain> {

    private static final FormaPagoDTOAssembler INSTANCIA = new FormaPagoDTOAssembler();

    private FormaPagoDTOAssembler() {
        super();
    }

    public static FormaPagoDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public FormaPagoDomain toDomain(final FormaPagoDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return FormaPagoDomain.obtenerValorDefecto();
        }
        return FormaPagoDomain.crear(dto.getCodigo(), dto.getNombre());
    }

    @Override
    public FormaPagoDTO toDto(final FormaPagoDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return FormaPagoDTO.obtenerValorDefecto();
        }
        return FormaPagoDTO.builder()
                .codigo(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
                .crear();
    }

    @Override
    public List<FormaPagoDomain> toDomainList(final List<FormaPagoDTO> dtoList) {
        final List<FormaPagoDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<FormaPagoDTO> toDtoList(final List<FormaPagoDomain> domainList) {
        final List<FormaPagoDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}