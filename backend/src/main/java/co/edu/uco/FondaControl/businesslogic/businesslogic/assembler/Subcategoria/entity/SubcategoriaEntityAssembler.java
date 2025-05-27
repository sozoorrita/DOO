package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Subcategoria.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SubcategoriaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.SubcategoriaEntity;

public final class SubcategoriaEntityAssembler implements EntityAssembler<SubcategoriaEntity, SubcategoriaDomain> {

    private static final SubcategoriaEntityAssembler INSTANCIA = new SubcategoriaEntityAssembler();

    private SubcategoriaEntityAssembler() {
        super();
    }

    public static SubcategoriaEntityAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public SubcategoriaEntity toEntity(final SubcategoriaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return SubcategoriaEntity.obtenerValorDefecto();
        }
        return SubcategoriaEntity.builder()
                .codigo(domain.getCodigo())
                .nombre(domain.getNombre())
                .codigoCategoria(domain.getCodigoCategoria())
                .crear();
    }

    @Override
    public SubcategoriaDomain toDomain(final SubcategoriaEntity entity) {
        final var safe = SubcategoriaEntity.obtenerValorDefecto(entity);
        return new SubcategoriaDomain(safe.getCodigo(), safe.getNombre(), safe.getCodigoCategoria());
    }

    @Override
    public List<SubcategoriaDomain> toDomainList(final List<SubcategoriaEntity> entityList) {
        final List<SubcategoriaDomain> resultado = new ArrayList<>();
        for (final var entity : entityList) {
            resultado.add(toDomain(entity));
        }
        return resultado;
    }
}







