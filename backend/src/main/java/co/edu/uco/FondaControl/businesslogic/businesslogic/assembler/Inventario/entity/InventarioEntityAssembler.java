package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Inventario.entity;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InventarioDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.InventarioEntity;

import java.util.ArrayList;
import java.util.List;

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
                domain.getNombreProducto(),
                domain.getCantidad(),
                domain.getCodigoIndicador()
        );
    }

    @Override
    public InventarioDomain toDomain(final InventarioEntity entity) {
        final var inventarioEntity = InventarioEntity.obtenerValorDefecto(entity);


        final IndicadorInventarioDomain indicador = new IndicadorInventarioDomain(
                inventarioEntity.getCodigoIndicador(),
                ""
        );

        return new InventarioDomain(
                inventarioEntity.getCodigo(),
                inventarioEntity.getNombreProducto(),
                inventarioEntity.getCantidad(),
                indicador
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
