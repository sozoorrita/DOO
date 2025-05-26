package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EstadoMesa.entity;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.EstadoMesaEntity;

import java.util.ArrayList;
import java.util.List;

public final class EstadoMesaEntityAssembler implements EntityAssembler<EstadoMesaEntity, EstadoMesaDomain> {

    private static final EstadoMesaEntityAssembler INSTANCE = new EstadoMesaEntityAssembler();

    private EstadoMesaEntityAssembler() {
        super();
    }

    public static EstadoMesaEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public EstadoMesaEntity toEntity(final EstadoMesaDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? EstadoMesaEntity.obtenerValorDefecto()
                : EstadoMesaEntity.builder()
                .codigo(domain.getCodigo())
                .nombre(domain.getNombre())
                .crear();
    }

    @Override
    public EstadoMesaDomain toDomain(final EstadoMesaEntity entity) {
        final var estadoMesaEntity = EstadoMesaEntity.obtenerValorDefecto(entity);
        return EstadoMesaDomain.crear(estadoMesaEntity.getCodigo(), estadoMesaEntity.getNombre());
    }

    @Override
    public List<EstadoMesaDomain> toDomainList(final List<EstadoMesaEntity> entityList) {
        final var listaResultado = new ArrayList<EstadoMesaDomain>();
        for (final var entity : entityList) {
            listaResultado.add(toDomain(entity));
        }
        return listaResultado;
    }
}
