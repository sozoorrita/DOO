package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.dto.ProductoDTO;
import co.edu.uco.FondaControl.dto.SubcategoriaDTO;

public final class ProductoDTOAssembler implements DTOAssembler<ProductoDTO, ProductoDomain> {

    private static final ProductoDTOAssembler INSTANCE = new ProductoDTOAssembler();

    private ProductoDTOAssembler() {
        super();
    }

    public static ProductoDTOAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public ProductoDTO toDto(final ProductoDomain domain) {
        final var safeDomain = UtilObjeto.getInstancia().esNulo(domain)
                ? ProductoDomain.obtenerValorDefecto()
                : domain;

        final ProductoDTO dto = new ProductoDTO();
        dto.setCodigoProducto(safeDomain.getCodigo());
        dto.setNombre(safeDomain.getNombre());
        dto.setPrecioLugar(safeDomain.getPrecioLugar());
        dto.setPrecioLlevar(safeDomain.getPrecioLlevar());

        final SubcategoriaDTO subDto = SubcategoriaDTO.builder()
                .codigo(safeDomain.getCodigoSubcategoria())
                .crear();
        dto.setCodigoSubcategoria(subDto);

        dto.setLimiteCantidad(safeDomain.getLimiteCantidad());
        return dto;
    }

    @Override
    public ProductoDomain toDomain(final ProductoDTO dto) {
        if (UtilObjeto.getInstancia().esNulo(dto)) {
            return ProductoDomain.obtenerValorDefecto();
        }
        final SubcategoriaDTO subDto = UtilObjeto.getInstancia()
                .esNulo(dto.getCodigoSubcategoria())
                ? SubcategoriaDTO.obtenerValorDefecto()
                : dto.getCodigoSubcategoria();

        return new ProductoDomain(
                dto.getCodigoProducto(),
                dto.getNombre(),
                dto.getPrecioLugar(),
                dto.getPrecioLlevar(),
                subDto.getCodigo(),
                dto.getLimiteCantidad()
        );
    }

    @Override
    public List<ProductoDomain> toDomainList(final List<ProductoDTO> dtoList) {
        final List<ProductoDomain> list = new ArrayList<>();
        for (final ProductoDTO dto : dtoList) {
            list.add(toDomain(dto));
        }
        return list;
    }

    @Override
    public List<ProductoDTO> toDtoList(final List<ProductoDomain> domainList) {
        final List<ProductoDTO> list = new ArrayList<>();
        for (final ProductoDomain domain : domainList) {
            list.add(toDto(domain));
        }
        return list;
    }
}
