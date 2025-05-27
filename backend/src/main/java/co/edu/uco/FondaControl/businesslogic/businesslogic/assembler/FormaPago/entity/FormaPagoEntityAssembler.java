package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.FormaPago.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.FormaPagoEntity;

public final class FormaPagoEntityAssembler implements EntityAssembler<FormaPagoEntity, FormaPagoDomain> {

    private static final FormaPagoEntityAssembler INSTANCE = new FormaPagoEntityAssembler();

    private FormaPagoEntityAssembler() {
        super();
    }

    public static FormaPagoEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public FormaPagoEntity toEntity(final FormaPagoDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? FormaPagoEntity.obtenerValorDefecto()
                : FormaPagoEntity.builder()
                        .codigo(domain.getCodigo())
                        .nombre(domain.getNombre())
                        .crear();
    }

    @Override
    public FormaPagoDomain toDomain(final FormaPagoEntity entity) {
        final var defaultEntity = FormaPagoEntity.obtenerValorDefecto(entity);
        return FormaPagoDomain.crear(defaultEntity.getCodigo(), defaultEntity.getNombre());
    }

    @Override
    public List<FormaPagoDomain> toDomainList(final List<FormaPagoEntity>	entityList) {
        final var resultado = new ArrayList<FormaPagoDomain>();
        for (final var entity : entityList) {
            resultado.add(toDomain(entity));
        }
        return resultado;
    }
}