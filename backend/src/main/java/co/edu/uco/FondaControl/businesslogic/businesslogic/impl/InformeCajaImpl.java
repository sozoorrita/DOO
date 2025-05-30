package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.entity.InformeCajaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.entity.InformeCajaEntity;
import co.edu.uco.FondaControl.entity.VentaEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class InformeCajaImpl implements InformeCajaBusinessLogic {

    private final DAOFactory factory;
    private final InformeCajaEntityAssembler assembler = InformeCajaEntityAssembler.getInstance();

    private static final UUID FORMA_PAGO_EFECTIVO      = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID FORMA_PAGO_TRANSFERENCIA = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public InformeCajaImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void crearInformeCaja(InformeCajaDomain informeCajaDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDomain)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El informe de caja no puede ser nulo.",
                    "Se recibió InformeCajaDomain null en crearInformeCaja(...)"
            );
        }
        try {
            InformeCajaEntity entity = assembler.toEntity(informeCajaDomain);
            factory.getInformeCajaDAO().create(entity);
            informeCajaDomain.setCodigo(entity.getCodigo());
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudo crear el informe de caja.",
                    "Error técnico al crear InformeCaja: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void eliminarInformeCaja(InformeCajaDomain informeCajaDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDomain) || UtilObjeto.getInstancia().esNulo(informeCajaDomain.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código del informe de caja es obligatorio para eliminar.",
                    "InformeCajaDomain o su código es null en eliminarInformeCaja(...)"
            );
        }
        try {
            factory.getInformeCajaDAO().delete(informeCajaDomain.getCodigo());
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudo eliminar el informe de caja.",
                    "Error técnico al eliminar InformeCaja: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void consultarInformeCajaPorcodigo(InformeCajaDomain informeCajaDomain) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDomain) || UtilObjeto.getInstancia().esNulo(informeCajaDomain.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código del informe de caja es obligatorio para la consulta.",
                    "InformeCajaDomain o su código es null en consultarInformeCajaPorcodigo(...)"
            );
        }
        try {
            InformeCajaEntity entity = factory.getInformeCajaDAO()
                    .findById(informeCajaDomain.getCodigo());
            InformeCajaDomain resultado = assembler.toDomain(entity);
            // Copiamos los valores al objeto pasado por parámetro
            informeCajaDomain.setCodigoSesionTrabajo(resultado.getCodigoSesionTrabajo());
            informeCajaDomain.setTotalVenta(resultado.getTotalVenta());
            informeCajaDomain.setPagoEfectivo(resultado.getPagoEfectivo());
            informeCajaDomain.setPagoTransferencia(resultado.getPagoTransferencia());
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudo consultar el informe de caja por código.",
                    "Error técnico al consultar InformeCaja: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public List<InformeCajaDomain> consultarInformeCaja(InformeCajaDomain filtro) throws FondaControlException {
        // Si no se pasa filtro, creamos uno vacío
        if (UtilObjeto.getInstancia().esNulo(filtro)) {
            filtro = new InformeCajaDomain();
        }
        try {
            InformeCajaEntity filterEntity = assembler.toEntity(filtro);
            List<InformeCajaEntity> entities = factory.getInformeCajaDAO()
                    .listByFilter(filterEntity);
            return entities.stream()
                    .map(assembler::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudieron consultar los informes de caja.",
                    "Error técnico al listar InformeCaja: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    public void consolidarventasInformeCaja(final InformeCajaDomain informe) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informe)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El informe de caja no puede ser nulo.",
                    "Se recibió InformeCajaDomain null en consolidarventasInformeCaja(...)"
            );
        }
        if (UtilObjeto.getInstancia().esNulo(informe.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código del informe de caja es obligatorio.",
                    "InformeCajaDomain.getCodigo() devolvió null."
            );
        }
        if (UtilObjeto.getInstancia().esNulo(informe.getCodigoSesionTrabajo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "Debe especificarse la sesión de trabajo.",
                    "InformeCajaDomain.getCodigoSesionTrabajo() devolvió null."
            );
        }

        try {
            List<VentaEntity> ventas = factory.getVentaDAO()
                    .listByCodigo(informe.getCodigoSesionTrabajo());

            BigDecimal acumuladoTotal         = BigDecimal.ZERO;
            BigDecimal acumuladoEfectivo      = BigDecimal.ZERO;
            BigDecimal acumuladoTransferencia = BigDecimal.ZERO;

            for (VentaEntity venta : ventas) {
                BigDecimal monto = BigDecimal.valueOf(venta.getTotalVenta());
                monto = monto = monto = monto = UtilMoneda.asegurarNoNegativo(monto);

                acumuladoTotal = acumuladoTotal.add(monto);
                if (FORMA_PAGO_EFECTIVO.equals(venta.getCodigoFormaPago())) {
                    acumuladoEfectivo = acumuladoEfectivo.add(monto);
                } else if (FORMA_PAGO_TRANSFERENCIA.equals(venta.getCodigoFormaPago())) {
                    acumuladoTransferencia = acumuladoTransferencia.add(monto);
                }
            }

            informe.setTotalVenta(acumuladoTotal);
            informe.setPagoEfectivo(acumuladoEfectivo);
            informe.setPagoTransferencia(acumuladoTransferencia);

        } catch (Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudieron consolidar las ventas del informe de caja.",
                    "Error técnico al procesar ventas: " + e.getMessage(),
                    e
            );
        }
    }
}
