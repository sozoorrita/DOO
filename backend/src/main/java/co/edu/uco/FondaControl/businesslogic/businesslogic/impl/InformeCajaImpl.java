package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import java.math.BigDecimal;
import java.util.UUID;

public final class InformeCajaImpl implements InformeCajaBusinessLogic {

    private final DAOFactory factory;

    // UIDs de forma de pago
    private static final UUID FORMA_PAGO_EFECTIVO = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID FORMA_PAGO_TRANSFERENCIA = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public InformeCajaImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void consolidarventasInformeCaja(final InformeCajaDomain informeCajaDomain) throws FondaControlException {
        validarIntegridadInforme(informeCajaDomain);

        try {
            final var ventas = factory.getVentaDAO().listByCodigo(informeCajaDomain.getCodigoSesionTrabajo());

            BigDecimal totalVenta = BigDecimal.ZERO;
            BigDecimal totalEfectivo = BigDecimal.ZERO;
            BigDecimal totalTransferencia = BigDecimal.ZERO;

            for (final var venta : ventas) {
                final var monto = BigDecimal.valueOf(Math.max(0, venta.getTotalVenta()));
                totalVenta = totalVenta.add(monto);

                if (FORMA_PAGO_EFECTIVO.equals(venta.getCodigoFormaPago())) {
                    totalEfectivo = totalEfectivo.add(monto);
                } else if (FORMA_PAGO_TRANSFERENCIA.equals(venta.getCodigoFormaPago())) {
                    totalTransferencia = totalTransferencia.add(monto);
                }
            }

            informeCajaDomain.setTotalVenta(totalVenta);
            informeCajaDomain.setPagoEfectivo(totalEfectivo);
            informeCajaDomain.setPagoTransferencia(totalTransferencia);

        } catch (final Exception e) {
            throw BusinessLogicFondaControlException.reportar(
                    "No se pudieron consolidar las ventas del informe de caja.",
                    "Error técnico al procesar las ventas: " + e.getMessage(), e
            );
        }
    }

    private void validarIntegridadInforme(final InformeCajaDomain informe) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informe)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El informe de caja no puede ser nulo.",
                    "Se intentó consolidar un informe nulo en 'consolidarventasInformeCaja(...)'."
            );
        }

        if (UtilObjeto.getInstancia().esNulo(informe.getCodigo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "El código del informe de caja no puede ser nulo.",
                    "InformeCajaDomain.getCodigo() devolvió null."
            );
        }

        if (UtilObjeto.getInstancia().esNulo(informe.getCodigoSesionTrabajo())) {
            throw BusinessLogicFondaControlException.reportar(
                    "Debe estar asociado a una sesión de trabajo.",
                    "InformeCajaDomain.getCodigoSesionTrabajo() devolvió null."
            );
        }
    }
}
