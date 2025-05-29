package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.entity.SubcategoriaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.ProductoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.ProductoEntity;

public final class ProductoEntityAssembler implements EntityAssembler<ProductoEntity, ProductoDomain> {

    private static final ProductoEntityAssembler INSTANCE = new ProductoEntityAssembler();

    private ProductoEntityAssembler() {
        super();
    }

    public static ProductoEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public ProductoEntity toEntity(final ProductoDomain domain) {
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            return ProductoEntity.obtenerValorDefecto();
        }
        final var subEntity = SubcategoriaEntityAssembler.getInstancia().toEntity(domain.getSubcategoria());

        return ProductoEntity.builder()
                .codigo(domain.getCodigo())
                .nombre(domain.getNombre())
                .precioLugar(domain.getPrecioLugar())
                .precioLlevar(domain.getPrecioLlevar())
                .subcategoria(subEntity)
                .limiteCantidad(domain.getLimiteCantidad())
                .crear();
    }

    @Override
    public ProductoDomain toDomain(final ProductoEntity entity) {
        final ProductoEntity safe = ProductoEntity.obtenerValorDefecto(entity);
        final SubcategoriaDomain subDomain = SubcategoriaEntityAssembler.getInstancia().toDomain(safe.getSubcategoria());

        return new ProductoDomain(
                safe.getCodigo(),
                safe.getNombre(),
                safe.getPrecioLugar(),
                safe.getPrecioLlevar(),
                subDomain,
                safe.getLimiteCantidad()
        );
    }

    @Override
    public List<ProductoDomain> toDomainList(final List<ProductoEntity> entityList) {
        final List<ProductoDomain> list = new ArrayList<>();
        for (final ProductoEntity entity : entityList) {
            list.add(toDomain(entity));
        }
        return list;
    }
}
