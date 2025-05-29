package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.entity.ProductoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.InventarioEntity;

public final class InventarioEntityAssembler implements EntityAssembler<InventarioEntity, InventarioDomain> {

    private static final InventarioEntityAssembler INSTANCE = new InventarioEntityAssembler();

    private InventarioEntityAssembler() {
        super();
    }

    public static InventarioEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public InventarioEntity toEntity(final InventarioDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? InventarioEntity.obtenerValorDefecto()
                : new InventarioEntity(
                    domain.getCodigo(),
                    ProductoEntityAssembler.getInstance().toEntity(domain.getProducto()),
                    domain.getCantidad(),
                    domain.getCodigoIndicador()
                );
    }

    @Override
    public InventarioDomain toDomain(final InventarioEntity entity) {
        final var inventarioEntity = InventarioEntity.obtenerValorDefecto(entity);

        return new InventarioDomain(
                inventarioEntity.getCodigo(),
                ProductoEntityAssembler.getInstance().toDomain(inventarioEntity.getProducto()),
                inventarioEntity.getCantidad(),
                new IndicadorInventarioDomain(inventarioEntity.getCodigoIndicador(), "")
        );
    }

    @Override
    public List<InventarioDomain> toDomainList(final List<InventarioEntity> entityList) {
        final var listaResultado = new ArrayList<InventarioDomain>();
        for (final var entity : entityList) {
            listaResultado.add(toDomain(entity));
        }
        return listaResultado;
    }
}
