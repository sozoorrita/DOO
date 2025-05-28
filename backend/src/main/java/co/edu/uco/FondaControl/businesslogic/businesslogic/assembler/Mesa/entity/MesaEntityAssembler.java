package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Mesa.entity;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.EstadoMesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.MesaEntity;

import java.util.ArrayList;
import java.util.List;

public final class MesaEntityAssembler implements EntityAssembler<MesaEntity, MesaDomain> {

    private static final MesaEntityAssembler INSTANCE = new MesaEntityAssembler();

    private MesaEntityAssembler() {
        super();
    }

    public static MesaEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public MesaEntity toEntity(final MesaDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? MesaEntity.obtenerValorDefecto()
                : new MesaEntity(domain.getCodigo(), domain.getIdentificador(), domain.getEstado().getCodigo());
    }

    @Override
    public MesaDomain toDomain(final MesaEntity entity) {
        final var entidad = MesaEntity.obtenerValorDefecto(entity);
        return MesaDomain.crear(entidad.getCodigo(), entidad.getNombre(),
                // Asignamos solo el UUID del estado, el Domain completo debe resolverse en la capa business
                EstadoMesaDomain.crear(entidad.getCodigoEstadoMesa(), ""));
    }

    @Override
    public List<MesaDomain> toDomainList(final List<MesaEntity> entityList) {
        final var listaResultado = new ArrayList<MesaDomain>();
        for (final var entidad : entityList) {
            listaResultado.add(toDomain(entidad));
        }
        return listaResultado;
    }
}
