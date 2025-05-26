package co.edu.uco.FondaControl.businesslogic.facade.imp;

import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.assembler.InformeCaja.entity.InformeCajaEntityAssembler;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InformeCajaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

import java.util.ArrayList;

public final class InformeCajaImp implements InformeCajaFacade {

    private final DAOFactory daoFactory;
    private final InformeCajaBusinessLogic businessLogic;

    public InformeCajaImp() throws DataFondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new InformeCajaImpl(daoFactory);
    }

    @Override
    public void consolidarVentasInformeCaja(final InformeCajaDTO informeCajaDTO) throws FondaControlException {
        if (UtilObjeto.getInstancia().esNulo(informeCajaDTO) || UtilObjeto.getInstancia().esNulo(informeCajaDTO.getCodigo())) {
            throw DataFondaControlException.reportar(
                    "El informe de caja y su código no pueden ser nulos.",
                    "Se recibió un DTO nulo o sin código en consolidarVentasInformeCaja()."
            );
        }

        try {
            daoFactory.iniciarTransaccion();


            final var domain = new InformeCajaDomain(
                    informeCajaDTO.getCodigo(),
                    informeCajaDTO.getCodigoSesionTrabajo(),
                    informeCajaDTO.getFecha(),
                    informeCajaDTO.getTotalVenta(),
                    informeCajaDTO.getPagoEfectivo(),
                    informeCajaDTO.getPagoTransferencia(),
                    new ArrayList<>() // ventas vacías
            );

            businessLogic.consolidarventasInformeCaja(domain);


            final var entity = InformeCajaEntityAssembler.getInstance().toEntity(domain);
            daoFactory.getInformeCajaDAO().update(domain.getCodigo(), entity);

            daoFactory.confirmarTransaccion();
        } catch (final Exception excepcion) {
            daoFactory.cancelarTransaccion();
            throw DataFondaControlException.reportar(
                    "Error al consolidar las ventas del informe de caja.",
                    "Excepción técnica al consolidarVentasInformeCaja: " + excepcion.getMessage(),
                    excepcion
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }
}
