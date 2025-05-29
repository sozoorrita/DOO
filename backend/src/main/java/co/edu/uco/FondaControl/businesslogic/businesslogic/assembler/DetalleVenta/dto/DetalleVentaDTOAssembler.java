package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto.ProductoDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;

public final class DetalleVentaDTOAssembler implements DTOAssembler<DetalleVentaDTO, DetalleVentaDomain> {

    private static final DetalleVentaDTOAssembler INSTANCIA = new DetalleVentaDTOAssembler();

    private DetalleVentaDTOAssembler() { super(); }

    public static DetalleVentaDTOAssembler getInstancia() { return INSTANCIA; }

    @Override
    public DetalleVentaDomain toDomain(final DetalleVentaDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return DetalleVentaDomain.obtenerValorDefecto();
        }

        return new DetalleVentaDomain(
                dto.getCodigo(),
                ProductoDTOAssembler.getInstance().toDomain(dto.getProducto()),
                dto.getCodigoVenta(),
                dto.getPrecioAplicado(),
                dto.getCantidad()
        );
    }

    @Override
    public DetalleVentaDTO toDto(final DetalleVentaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return new DetalleVentaDTO();
        }

        return new DetalleVentaDTO(
                domain.getCodigoDetalleVenta(),
                ProductoDTOAssembler.getInstance().toDto(domain.getProducto()),
                domain.getCodigoVenta(),
                domain.getPrecioAplicado(),
                domain.getCantidad()
        );
    }

    @Override
    public List<DetalleVentaDomain> toDomainList(final List<DetalleVentaDTO> dtoList) {
        final List<DetalleVentaDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<DetalleVentaDTO> toDtoList(final List<DetalleVentaDomain> domainList) {
        final List<DetalleVentaDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
