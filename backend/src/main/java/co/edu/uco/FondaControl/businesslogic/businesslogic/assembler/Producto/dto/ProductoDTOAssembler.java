package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DTOAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.dto.ProductoDTO;

public final class ProductoDTOAssembler implements DTOAssembler<ProductoDTO, ProductoDomain> {

    private static final ProductoDTOAssembler INSTANCIA = new ProductoDTOAssembler();

    private ProductoDTOAssembler() {
        super();
    }

    public static ProductoDTOAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public ProductoDTO toDto(final ProductoDomain domain) {
        return ProductoDTO.builder()
                .codigoProducto(domain.getCodigo())
                .nombre(UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(domain.getNombre()))
                .precioLugar(domain.getPrecioLugar())
                .precioLlevar(domain.getPrecioLlevar())
                .limiteCantidad(domain.getLimiteCantidad())
                .crear();
    }

    @Override
    public ProductoDomain toDomain(final ProductoDTO dto) {
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
    public List<ProductoDTO> toDtoList(final List<ProductoDomain> domainList) {
        final List<ProductoDTO> resultado = new ArrayList<>();
        for (ProductoDomain domain : domainList) {
            resultado.add(toDto(domain));
        }
        return resultado;
    }

    @Override
    public List<ProductoDomain> toDomainList(final List<ProductoDTO> dtoList) {
        final List<ProductoDomain> resultado = new ArrayList<>();
        for (ProductoDTO dto : dtoList) {
            resultado.add(toDomain(dto));
        }
        return resultado;
    }
}
