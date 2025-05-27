package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.dto.ProductoDTO;

public final class ProductoDTOAssembler implements DTOAssembler<ProductoDTO, ProductoDomain> {

    private static final ProductoDTOAssembler INSTANCIA = new ProductoDTOAssembler();

    private ProductoDTOAssembler() { super(); }

    public static ProductoDTOAssembler getInstancia() { return INSTANCIA; }

    @Override
    public ProductoDomain toDomain(final ProductoDTO dto) {
        if (UtilObjeto.esNulo(dto)) {
            return ProductoDomain.obtenerValorDefecto();
        }
        return new ProductoDomain(
            dto.getCodigoProducto(),
            dto.getNombre(),
            dto.getPrecioLugar(),
            dto.getPrecioLlevar(),
            dto.getCodigoSubcategoria(),
            dto.getLimiteCantidad()
        );
    }

    @Override
    public ProductoDTO toDto(final ProductoDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return ProductoDTO.obtenerValorDefecto();
        }
        return ProductoDTO.builder()
            .id(domain.getCodigo())
            .nombre(UtilTexto.getInstancia().obtenerValorDefecto(domain.getNombre()))
            .precioLugar(domain.getPrecioLugar())
            .precioLlevar(domain.getPrecioLlevar())
            .subcategoria(domain.getCodigoSubcategoria())
            .limiteCantidad(domain.getLimiteCantidad())
            .crear();
    }

    @Override
    public List<ProductoDomain> toDomainList(final List<ProductoDTO> dtoList) {
        final List<ProductoDomain> resultado = new ArrayList<>();
        for (ProductoDTO dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }

    @Override
    public List<ProductoDTO> toDtoList(final List<ProductoDomain> domainList) {
        final List<ProductoDTO> resultado = new ArrayList<>();
        for (ProductoDomain domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }
}
