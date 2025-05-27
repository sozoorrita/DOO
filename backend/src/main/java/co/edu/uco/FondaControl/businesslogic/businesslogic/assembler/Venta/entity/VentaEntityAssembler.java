package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.VentaEntity;

public final class VentaEntityAssembler implements EntityAssembler<VentaEntity, VentaDomain> {

    private static final VentaEntityAssembler INSTANCE = new VentaEntityAssembler();

    private VentaEntityAssembler() {
        super();
    }

    public static VentaEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public VentaEntity toEntity(final VentaDomain domain) {
        if (UtilObjeto.getInstancia().esNulo(domain)) {
            return VentaEntity.obtenerValorDefecto();
        }
        return VentaEntity.builder()
                .codigo(domain.getCodigoVenta())
                .fecha(domain.getFecha())
                .totalVenta(domain.getTotalVenta())
                .codigoFormaPago(domain.getCodigoFormaPago())
                .codigoTipoVenta(domain.getCodigoTipoVenta())
                .codigoSesionTrabajo(domain.getCodigoSesionTrabajo())
                .codigoMesa(domain.getCodigoMesa())
                .crear();
    }

    @Override
    public VentaDomain toDomain(final VentaEntity entity) {
        final VentaEntity safe = VentaEntity.obtenerValorDefecto(entity);
        return new VentaDomain(
                safe.getCodigo(),
                safe.getFecha(),
                safe.getTotalVenta(),
                safe.getCodigoFormaPago(),
                safe.getCodigoTipoVenta(),
                safe.getCodigoSesionTrabajo(),
                safe.getCodigoMesa()
        );
    }

    @Override
    public List<VentaDomain> toDomainList(final List<VentaEntity> entityList) {
        final List<VentaDomain> list = new ArrayList<>();
        for (final VentaEntity entity : entityList) {
            list.add(toDomain(entity));
        }
        return list;
    }
}
