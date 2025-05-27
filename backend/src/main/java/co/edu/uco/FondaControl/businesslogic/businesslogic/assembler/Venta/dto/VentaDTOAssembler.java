package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.FormaPago.dto.FormaPagoDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.TipoVenta.dto.TipoVentaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.SesionTrabajo.dto.SesionTrabajoDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.dto.MesaDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.dto.VentaDTO;
import co.edu.uco.FondaControl.dto.FormaPagoDTO;
import co.edu.uco.FondaControl.dto.TipoVentaDTO;
import co.edu.uco.FondaControl.dto.SesionTrabajoDTO;
import co.edu.uco.FondaControl.dto.MesaDTO;

public final class VentaDTOAssembler implements DTOAssembler<VentaDTO, VentaDomain> {

    private static final VentaDTOAssembler INSTANCIA = new VentaDTOAssembler();

    private VentaDTOAssembler() {
        super();
    }

    public static VentaDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public VentaDTO toDto(final VentaDomain domain) {
        return VentaDTO.builder()
                .codigo(domain.getCodigoVenta())
                .fecha(domain.getFecha())
                .totalVenta(domain.getTotalVenta())
                .codigoFormaPago(domain.getCodigoFormaPago())
                .codigoTipoVenta(domain.getCodigoTipoVenta())
                .codigoSesionTrabajo(domain.getCodigoSesionTrabajo())
                .codigoMesa(domain.getCodigoMesa())
                .crear();
    }

    @Override
    public VentaDomain toDomain(final VentaDTO dto) {
        return new VentaDomain(
                dto.getCodigo(),
                dto.getFecha(),
                dto.getTotalVenta(),
                dto.getCodigoFormaPago(),
                dto.getCodigoTipoVenta(),
                dto.getCodigoSesionTrabajo(),
                dto.getCodigoMesa()
        );
    }

    @Override
    public List<VentaDTO> toDtoList(final List<VentaDomain> domainList) {
        final List<VentaDTO> resultado = new ArrayList<>();
        for (VentaDomain domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }

    @Override
    public List<VentaDomain> toDomainList(final List<VentaDTO> dtoList) {
        final List<VentaDomain> resultado = new ArrayList<>();
        for (VentaDTO dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }
}