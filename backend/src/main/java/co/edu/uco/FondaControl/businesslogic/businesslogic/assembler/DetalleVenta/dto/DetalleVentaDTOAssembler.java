package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.DetalleVentaDTO;

public final class DetalleVentaDTOAssembler implements DTOAssembler<DetalleVentaDTO, DetalleVentaDomain> {

    private static final DetalleVentaDTOAssembler INSTANCIA = new DetalleVentaDTOAssembler();

    private DetalleVentaDTOAssembler() {
        super();
    }

    public static DetalleVentaDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public DetalleVentaDTO toDto(final DetalleVentaDomain domain) {
        return DetalleVentaDTO.builder()
                .codigoDetalleVenta(domain.getCodigoDetalleVenta())
                .nombreProducto(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombreProducto()))
                .precioAplicado(domain.getPrecioAplicado())
                .cantidad(domain.getCantidad())
                .crear();
    }

    @Override
    public DetalleVentaDomain toDomain(final DetalleVentaDTO dto) {
        return new DetalleVentaDomain(
                dto.getCodigoDetalleVenta(),
                dto.getNombreProducto(),
                dto.getPrecioAplicado(),
                dto.getCantidad()
        );
    }

    @Override
    public List<DetalleVentaDTO> toDtoList(final List<DetalleVentaDomain> domainList) {
        final List<DetalleVentaDTO> resultado = new ArrayList<>();
        for (DetalleVentaDomain domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }

    @Override
    public List<DetalleVentaDomain> toDomainList(final List<DetalleVentaDTO> dtoList) {
        final List<DetalleVentaDomain> resultado = new ArrayList<>();
        for (DetalleVentaDTO dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }
}