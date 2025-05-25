package co.edu.uco.FondaControl.businesslogic.facade.imp;


import co.edu.uco.FondaControl.businesslogic.businesslogic.InformeCajaBusinessLogic;
import co.edu.uco.FondaControl.businesslogic.businesslogic.domain.InformeCajaDomain;
import co.edu.uco.FondaControl.businesslogic.businesslogic.impl.InformeCajaImpl;
import co.edu.uco.FondaControl.businesslogic.facade.InformeCajaFacade;
import co.edu.uco.FondaControl.crosscutting.excepciones.FondaControlException;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.excepciones.DataFondaControlException;
import co.edu.uco.FondaControl.data.dao.factory.DAOFactory;
import co.edu.uco.FondaControl.data.dao.factory.Factory;
import co.edu.uco.FondaControl.dto.InformeCajaDTO;

public class InformeCajaImp implements InformeCajaFacade {

    private final DAOFactory daoFactory;
    private final InformeCajaBusinessLogic businessLogic;

    public InformeCajaImp() throws FondaControlException {
        this.daoFactory = DAOFactory.getDAOFactory(Factory.POSTGRESQL);
        this.businessLogic = new InformeCajaImpl(); // usar implementación de lógica
    }

    @Override
    public void consolidarVentasInformeCaja(InformeCajaDTO informeCaja) throws FondaControlException {
        if (UtilObjeto.esNulo(informeCaja) || UtilObjeto.esNulo(informeCaja.getCodigo())) {
            throw DataFondaControlException.reportar(
                    "El informe de caja y su código no pueden ser nulos.",
                    "Se recibió un DTO nulo o sin código en consolidarVentasInformeCaja()"
            );
        }

        try {
            daoFactory.iniciarTransaccion();


            InformeCajaDomain domain = mapToDomain(informeCaja);


            businessLogic.consolidarventasInformeCaja(domain);


            daoFactory.getInformeCajaDAO().update(informeCaja);

            daoFactory.confirmarTransaccion();
        } catch (Exception e) {
            daoFactory.cancelarTransaccion();
            throw DataFondaControlException.reportar(
                    "Error al consolidar las ventas del informe de caja.",
                    "Error técnico: " + e.getMessage(),
                    e
            );
        } finally {
            daoFactory.cerrarConexion();
        }
    }

    private InformeCajaDomain mapToDomain(InformeCajaDTO dto) {
        return new InformeCajaDomain(
                dto.getCodigo(),
                dto.getCodigoSesionTrabajo(),
                dto.getFecha(),
                dto.getTotalVenta(),
                dto.getPagoEfectivo(),
                dto.getPagoTransferencia(),
                java.util.List.of()
        );
    }
}
