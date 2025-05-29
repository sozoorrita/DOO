package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto.ProductoDTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.dto.InventarioDTO;

public final class InventarioDTOAssembler implements DTOAssembler<InventarioDTO, InventarioDomain> {

    private static final InventarioDTOAssembler INSTANCIA = new InventarioDTOAssembler();

    private InventarioDTOAssembler() { super(); }

    public static InventarioDTOAssembler getInstancia() { return INSTANCIA; }

    @Override
    public InventarioDomain toDomain(final InventarioDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return InventarioDomain.obtenerValorDefecto();
        }

        return new InventarioDomain(
                dto.getCodigo(),
                ProductoDTOAssembler.getInstance().toDomain(dto.getProducto()),
                dto.getCantidad(),
                null // Si tienes un DTO para el indicador, llama aquí el assembler, si no, pon null o como corresponda.
        );
    }

    @Override
    public InventarioDTO toDto(final InventarioDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return new InventarioDTO();
        }

        return new InventarioDTO(
                domain.getCodigo(),
                ProductoDTOAssembler.getInstance().toDto(domain.getProducto()),
                domain.getCantidad(),
                domain.getCodigoIndicador()
                // Puedes agregar el objeto indicador si tienes ese assembler y lo necesitas en el DTO.
        );
    }

    @Override
    public List<InventarioDomain> toDomainList(final List<InventarioDTO> dtoList) {
        final List<InventarioDomain> resultado = new ArrayList<>();
        for (final var dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<InventarioDTO> toDtoList(final List<InventarioDomain> domainList) {
        final List<InventarioDTO> resultado = new ArrayList<>();
        for (final var domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
