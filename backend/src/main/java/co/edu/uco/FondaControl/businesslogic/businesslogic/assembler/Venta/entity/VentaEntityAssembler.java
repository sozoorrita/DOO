package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.FormaPagoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.MesaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.SesionTrabajoDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.TipoVentaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
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
        if (domain == null) {
            return VentaEntity.obtenerValorDefecto();
        }
        return VentaEntity.builder()
                .codigo(domain.getCodigoVenta())
                .fecha(domain.getFecha())
                .totalVenta(domain.getTotalVenta())
                .codigoFormaPago(domain.getFormaPago() != null ? domain.getFormaPago().getCodigo() : null)
                .codigoTipoVenta(domain.getTipoVenta() != null ? domain.getTipoVenta().getCodigo() : null)
                .codigoSesionTrabajo(domain.getSesionTrabajo() != null ? domain.getSesionTrabajo().getCodigo() : null)
                .codigoMesa(domain.getMesa() != null ? domain.getMesa().getCodigo() : null)
                .crear();
    }

    @Override
    public VentaDomain toDomain(final VentaEntity entity) {
        final VentaEntity safe = VentaEntity.obtenerValorDefecto(entity);
        return new VentaDomain(
                safe.getCodigo(),
                safe.getFecha(),
                safe.getTotalVenta(),
                new FormaPagoDomain(safe.getCodigoFormaPago(), ""),
                new TipoVentaDomain(safe.getCodigoTipoVenta(), ""),
                new SesionTrabajoDomain(safe.getCodigoSesionTrabajo(), null, null, null, null),
                new MesaDomain(safe.getCodigoMesa(), "", null)
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
