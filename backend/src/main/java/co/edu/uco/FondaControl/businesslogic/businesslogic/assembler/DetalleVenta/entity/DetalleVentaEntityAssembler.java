package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.DetalleVenta.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Producto.entity.ProductoEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.DetalleVentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.DetalleVentaEntity;

public final class DetalleVentaEntityAssembler implements EntityAssembler<DetalleVentaEntity, DetalleVentaDomain> {

    private static final DetalleVentaEntityAssembler INSTANCE = new DetalleVentaEntityAssembler();

    private DetalleVentaEntityAssembler() {
        super();
    }

    public static DetalleVentaEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public DetalleVentaEntity toEntity(final DetalleVentaDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? DetalleVentaEntity.obtenerValorDefecto()
                : DetalleVentaEntity.builder()
                    .codigo(domain.getCodigoDetalleVenta())
                    .producto(ProductoEntityAssembler.getInstance().toEntity(domain.getProducto()))
                    .codigoVenta(domain.getCodigoVenta())
                    .precioAplicado(domain.getPrecioAplicado())
                    .cantidad(domain.getCantidad())
                    .crear();
    }

    @Override
    public DetalleVentaDomain toDomain(final DetalleVentaEntity entity) {
        final var safe = DetalleVentaEntity.obtenerValorDefecto(entity);
        return new DetalleVentaDomain(
                safe.getCodigo(),
                ProductoEntityAssembler.getInstance().toDomain(safe.getProducto()),
                safe.getCodigoVenta(),
                safe.getPrecioAplicado(),
                safe.getCantidad()
        );
    }

    @Override
    public List<DetalleVentaDomain> toDomainList(final List<DetalleVentaEntity> entityList) {
        final var result = new ArrayList<DetalleVentaDomain>();
        for (final var entity : entityList) {
            result.add(toDomain(entity));
        }
        return result;
    }
}
