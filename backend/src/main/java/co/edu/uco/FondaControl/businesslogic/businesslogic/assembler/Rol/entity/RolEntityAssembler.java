package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Rol.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.RolDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.RolEntity;

public final class RolEntityAssembler implements EntityAssembler<RolEntity, RolDomain> {

    private static final RolEntityAssembler INSTANCE = new RolEntityAssembler();

    private RolEntityAssembler() {
        super();
    }

    public static RolEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public RolEntity toEntity(final RolDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? RolEntity.obtenerValorDefecto()
                : RolEntity.builder()
                        .codigo(domain.getCodigo())
                        .nombre(domain.getNombre())
                        .crear();
    }

    @Override
    public RolDomain toDomain(final RolEntity entity) {
        final var defaultEntity = RolEntity.obtenerValorDefecto(entity);
        return RolDomain.crear(defaultEntity.getCodigo(), defaultEntity.getNombre());
    }

    @Override
    public List<RolDomain> toDomainList(final List<RolEntity> entityList) {
        final var resultado = new ArrayList<RolDomain>();
        for (final var entity : entityList) {
            resultado.add(toDomain(entity));
        }
        return resultado;
    }
}