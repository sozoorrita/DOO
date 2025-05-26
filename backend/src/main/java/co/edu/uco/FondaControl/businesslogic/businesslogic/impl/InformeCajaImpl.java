package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain.Venta;
import co.edu.uco.FondaControl.crosscutting.excepciones.BusinessLogicFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;

import java.math.BigDecimal;

public final class InformeCajaImpl implements InformeCajaBusinessLogic {

    private final DAOFactory factory;

    public InformeCajaImpl(final DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void consolidarventasInformeCaja(final InformeCajaDomain informeCajaDomain) throws FondaControlException {
        validarIntegridadInforme(informeCajaDomain);

        final var totalVentas = informeCajaDomain.getVentas()
                .stream()
                .map(Venta::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        informeCajaDomain.setTotalVenta(UtilMoneda.asegurarNoNegativo(totalVentas));
    }



    private void validarIntegridadInforme(final InformeCajaDomain informe) throws BusinessLogicFondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informe)) {
            throw BusinessLogicFondaControlException.reportar(
                    "El informe de caja no puede ser nulo.",
                    "Se intentó consolidar un informe nulo en 'consolidarventasInformeCaja(...)'."
            );
        }

        if (UtilObjeto.getInstancia().esNulo(informe.getVentas())) {
            throw BusinessLogicFondaControlException.reportar(
                    "La lista de ventas en el informe de caja no puede ser nula.",
                    "InformeCajaDomain.getVentas() devolvió null al intentar consolidar."
            );
        }
    }
}
