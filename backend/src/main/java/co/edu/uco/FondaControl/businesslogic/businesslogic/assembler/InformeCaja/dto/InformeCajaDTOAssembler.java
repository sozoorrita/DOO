package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilFecha;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

public final class InformeCajaDTOAssembler implements DTOAssembler<InformeCajaDTO, InformeCajaDomain> {

    private static final InformeCajaDTOAssembler INSTANCIA = new InformeCajaDTOAssembler();

    private InformeCajaDTOAssembler() {
        super();
    }

    public static InformeCajaDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public InformeCajaDomain toDomain(final InformeCajaDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return InformeCajaDomain.obtenerValorDefecto();
        }

        return new InformeCajaDomain(
                dto.getCodigo(),
                dto.getCodigoSesionTrabajo(),
                (dto.getFecha() != null) ? dto.getFecha() : UtilFecha.obtenerValorDefecto().toLocalDate(),
                UtilMoneda.obtenerValorDefecto(dto.getTotalVenta()),
                UtilMoneda.obtenerValorDefecto(dto.getPagoEfectivo()),
                UtilMoneda.obtenerValorDefecto(dto.getPagoTransferencia())
        );

    }

    @Override
    public InformeCajaDTO toDto(final InformeCajaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return new InformeCajaDTO();
        }

        return new InformeCajaDTO(
                domain.getCodigo(),
                domain.getCodigoSesionTrabajo(),
                domain.getFecha(),
                domain.getTotalVenta(),
                domain.getPagoEfectivo(),
                domain.getPagoTransferencia()
        );
    }

    @Override
    public List<InformeCajaDomain> toDomainList(final List<InformeCajaDTO> dtoList) {
        final List<InformeCajaDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<InformeCajaDTO> toDtoList(final List<InformeCajaDomain> domainList) {
        final List<InformeCajaDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
