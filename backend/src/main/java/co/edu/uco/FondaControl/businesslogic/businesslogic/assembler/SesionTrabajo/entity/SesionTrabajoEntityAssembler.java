package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.SesionTrabajo.entity;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Usuario.entity.UsuarioEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.SesionTrabajoEntity;

import java.util.ArrayList;
import java.util.List;

public final class SesionTrabajoEntityAssembler implements EntityAssembler<SesionTrabajoEntity, SesionTrabajoDomain> {

    private static final SesionTrabajoEntityAssembler INSTANCE = new SesionTrabajoEntityAssembler();

    private SesionTrabajoEntityAssembler() {
        super();
    }

    public static SesionTrabajoEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public SesionTrabajoEntity toEntity(final SesionTrabajoDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? new SesionTrabajoEntity()
                : new SesionTrabajoEntity(
                domain.getCodigo(),
                UsuarioEntityAssembler.getInstance().toEntity(domain.getUsuario()),
                domain.getBaseCaja(),
                domain.getFechaApertura(),
                domain.getFechaCierre()
        );
    }

    @Override
    public SesionTrabajoDomain toDomain(final SesionTrabajoEntity entity) {
        final var entidad = UtilObjeto.getInstancia().obtenerValorDefecto(entity, new SesionTrabajoEntity());
        return SesionTrabajoDomain.crear(
                entidad.getCodigo(),
                UsuarioEntityAssembler.getInstance().toDomain(entidad.getIdUsuario()),
                entidad.getBaseCaja(),
                entidad.getFechaApertura(),
                entidad.getFechaCierre()
        );
    }

    @Override
    public List<SesionTrabajoDomain> toDomainList(final List<SesionTrabajoEntity> entityList) {
        final var listaResultado = new ArrayList<SesionTrabajoDomain>();
        for (final var entidad : entityList) {
            listaResultado.add(toDomain(entidad));
        }
        return listaResultado;
    }
}
