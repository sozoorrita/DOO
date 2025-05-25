package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.IndicadorInventario;

import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.IndicadorInventarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.IndicadorInventarioEntity;

import java.util.ArrayList;
import java.util.List;

public final class IndicadorInventarioEntityAssembler implements EntityAssembler<IndicadorInventarioEntity, IndicadorInventarioDomain> {

    private static final IndicadorInventarioEntityAssembler INSTANCE = new IndicadorInventarioEntityAssembler();

    private IndicadorInventarioEntityAssembler() {
        super();
    }

    public static IndicadorInventarioEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public IndicadorInventarioEntity toEntity(final IndicadorInventarioDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? IndicadorInventarioEntity.obtenerValorDefecto()
                : new IndicadorInventarioEntity(domain.getCodigo(), domain.getNombre());
    }

    @Override
    public IndicadorInventarioDomain toDomain(final IndicadorInventarioEntity entity) {
        final var entidad = IndicadorInventarioEntity.obtenerValorDefecto(entity);
        return new IndicadorInventarioDomain(entidad.getCodigo(), entidad.getNombre());
    }

    @Override
    public List<IndicadorInventarioDomain> toDomainList(final List<IndicadorInventarioEntity> entityList) {
        final var listaResultado = new ArrayList<IndicadorInventarioDomain>();
        for (final var entidad : entityList) {
            listaResultado.add(toDomain(entidad));
        }
        return listaResultado;
    }
}
