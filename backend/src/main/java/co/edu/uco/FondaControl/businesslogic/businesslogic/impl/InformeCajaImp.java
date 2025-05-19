package backend.src.main.java.co.edu.uco.FondaControl.businesslogic.businesslogic.impl;


import backend.src.main.java.co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.math.BigDecimal;

public class InformeCajaImp implements InformeCajaBusinessLogic {

    @Override
    public void consolidarventasInformeCaja(InformeCajaDomain informeCajaDomain) {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDomain) || UtilObjeto.getInstancia().esNulo(informeCajaDomain.getVentas())) {
            return;
        }

        BigDecimal totalVentas = informeCajaDomain.getVentas().stream()
                .map(InformeCajaDomain.Venta::getMonto) // Obtiene el monto de cada venta
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Suma todos los montos

        informeCajaDomain.setTotalVenta(totalVentas);
    }
}