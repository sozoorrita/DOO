package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Usuario.entity;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.UsuarioDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.UsuarioEntity;

import java.util.ArrayList;
import java.util.List;

public final class UsuarioEntityAssembler implements EntityAssembler<UsuarioEntity, UsuarioDomain> {

    private static final UsuarioEntityAssembler INSTANCE = new UsuarioEntityAssembler();

    private UsuarioEntityAssembler() {
        super();
    }

    public static UsuarioEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public UsuarioEntity toEntity(final UsuarioDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? UsuarioEntity.obtenerValorDefecto()
                : new UsuarioEntity(domain.getId(), domain.getNombre(), domain.getCodigoRol(), domain.getContrasena());
    }

    @Override
    public UsuarioDomain toDomain(final UsuarioEntity entity) {
        final var entidad = UtilObjeto.getInstancia().obtenerValorDefecto(entity, UsuarioEntity.obtenerValorDefecto());
        return new UsuarioDomain(entidad.getId(), entidad.getNombre(), entidad.getCodigoRol(), entidad.getContrasena());
    }

    @Override
    public List<UsuarioDomain> toDomainList(final List<UsuarioEntity> entityList) {
        final var listaResultado = new ArrayList<UsuarioDomain>();
        for (final var entidad : entityList) {
            listaResultado.add(toDomain(entidad));
        }
        return listaResultado;
    }
}
