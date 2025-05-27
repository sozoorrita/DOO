package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.Venta.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.VentaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.VentaEntity;

public final class VentaEntityAssembler implements EntityAssembler<VentaEntity, VentaDomain> {

    private static final VentaEntityAssembler INSTANCIA = new VentaEntityAssembler();

    private VentaEntityAssembler() {
        super();
    }

    public static VentaEntityAssembler getInstancia() {
        return INSTANCIA;
    }

    @Override
    public VentaEntity toEntity(final VentaDomain domain) {
        if (UtilObjeto.esNulo(domain)) {
            return VentaEntity.obtenerValorDefecto();
        }
        var entity = new VentaEntity();
        entity.setCodigoVenta(domain.getCodigoVenta());
        entity.setFecha(domain.getFecha());
        entity.setTotalVenta(domain.getTotalVenta());
        entity.setCodigoFormaPago(domain.getCodigoFormaPago());
        entity.setCodigoTipoVenta(domain.getCodigoTipoVenta());
        entity.setCodigoSesionTrabajo(domain.getCodigoSesionTrabajo());
        entity.setCodigoMesa(domain.getCodigoMesa());
        return entity;
    }

    @Override
    public VentaDomain toDomain(final VentaEntity entity) {
        var safe = VentaEntity.obtenerValorDefecto(entity);
        return new VentaDomain(
                safe.getCodigoVenta(),
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
        final List<VentaDomain> resultado = new ArrayList<>();
        for (VentaEntity entity : entityList) {
            resultado.add(toDomain(entity));
        }
        return resultado;
    }
}