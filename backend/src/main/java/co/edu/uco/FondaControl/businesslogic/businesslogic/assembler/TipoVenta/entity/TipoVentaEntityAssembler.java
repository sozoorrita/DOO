package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.TipoVenta.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.TipoVentaEntity;

public final class TipoVentaEntityAssembler implements EntityAssembler<TipoVentaEntity, TipoVentaDomain> {

    private static final TipoVentaEntityAssembler INSTANCE = new TipoVentaEntityAssembler();

    private TipoVentaEntityAssembler() {
        super();
    }

    public static TipoVentaEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public TipoVentaEntity toEntity(final TipoVentaDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? TipoVentaEntity.obtenerValorDefecto()
                : TipoVentaEntity.builder()
                        .codigo(domain.getCodigo())
                        .nombre(domain.getNombre())
                        .crear();
    }

    @Override
    public TipoVentaDomain toDomain(final TipoVentaEntity entity) {
        final var defaultEntity = TipoVentaEntity.obtenerValorDefecto(entity);
        return TipoVentaDomain.crear(defaultEntity.getCodigo(), defaultEntity.getNombre());
    }

    @Override
    public List<TipoVentaDomain> toDomainList(final List<TipoVentaEntity> entityList) {
        final var resultado = new ArrayList<TipoVentaDomain>();
        for (final var entity : entityList) {
            resultado.add(toDomain(entity));
        }
        return resultado;
    }
}