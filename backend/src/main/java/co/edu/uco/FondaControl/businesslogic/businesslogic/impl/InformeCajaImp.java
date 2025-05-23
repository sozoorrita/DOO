package co.edu.uco.FondaControl.businesslogic.businesslogic.impl;


import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain.Venta;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilMoneda;

import java.math.BigDecimal;

public class InformeCajaImp implements InformeCajaBusinessLogic {

    @Override
    public void consolidarventasInformeCaja(InformeCajaDomain informeCajaDomain) {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDomain) || UtilObjeto.getInstancia().esNulo(informeCajaDomain.getVentas())) {
            return;
        }

        BigDecimal totalVentas = informeCajaDomain.getVentas()
                .stream()
                .map(Venta::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        informeCajaDomain.setTotalVenta(UtilMoneda.asegurarNoNegativo(totalVentas));
    }
}
