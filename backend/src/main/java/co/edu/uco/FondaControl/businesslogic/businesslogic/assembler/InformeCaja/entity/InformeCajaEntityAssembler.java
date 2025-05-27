package co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.entity;

import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.EntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;

import java.util.ArrayList;
import java.util.List;

public final class InformeCajaEntityAssembler implements EntityAssembler<InformeCajaEntity, InformeCajaDomain> {

    private static final InformeCajaEntityAssembler INSTANCE = new InformeCajaEntityAssembler();

    private InformeCajaEntityAssembler() {
        super();
    }

    public static InformeCajaEntityAssembler getInstance() {
        return INSTANCE;
    }

    @Override
    public InformeCajaEntity toEntity(final InformeCajaDomain domain) {
        return UtilObjeto.getInstancia().esNulo(domain)
                ? InformeCajaEntity.obtenerValorDefecto()
                : new InformeCajaEntity(
                domain.getCodigo(),
                domain.getCodigoSesionTrabajo(),
                domain.getFecha(),
                domain.getTotalVenta(),
                domain.getPagoEfectivo(),
                domain.getPagoTransferencia()
        );
    }

    @Override
    public InformeCajaDomain toDomain(final InformeCajaEntity entity) {
        final var entidad = UtilObjeto.getInstancia().obtenerValorDefecto(entity, InformeCajaEntity.obtenerValorDefecto());

        return new InformeCajaDomain(
                entidad.getCodigo(),
                entidad.getCodigoSesionTrabajo(),
                entidad.getFecha(),
                entidad.getTotalVenta(),
                entidad.getPagoEfectivo(),
                entidad.getPagoTransferencia()
        );
    }

    @Override
    public List<InformeCajaDomain> toDomainList(final List<InformeCajaEntity> entityList) {
        final var listaResultado = new ArrayList<InformeCajaDomain>();
        for (final var entidad : entityList) {
            listaResultado.add(toDomain(entidad));
        }
        return listaResultado;
    }
}
